// ������ 3
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Position_17142 {
	public int y,x;

	public Position_17142(int y, int x) {
		super();
		this.y = y;
		this.x = x;
	}		
}

public class Problem_17142 {
	
	static int N;
	static int[][] Map;
	static int M;
	static ArrayList<Position_17142> virusList;
	static int[] arr;
	
	static int[][] directions = {
			{-1, 0, 1, 0},
			{0, 1, 0, -1}
	};
	
	static int Time;
	static int result;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		String input = br.readLine();
		st = new StringTokenizer(input, " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		Map = new int[N][N];
		
		// input map & virus
		virusList = new ArrayList<>();
		int cnt=0;
		for(int i=0; i<N; ++i) {
			input = br.readLine();
			st = new StringTokenizer(input, " ");
			
			for(int j=0; j<N; ++j) {
				Map[i][j] = Integer.parseInt(st.nextToken());
				if(Map[i][j] == 2) {
					virusList.add(new Position_17142(i, j));
				}
				
				if(Map[i][j] == 0) cnt++;
			}
		}
		
		// ��ĭ�� ���ٸ�
		if(cnt == 0) System.out.println(0);
		else {
			result = Integer.MAX_VALUE;
			doSolution();
			
			int answer = result == Integer.MAX_VALUE ? -1 : result;
			System.out.println(answer);
		}		
	}

	private static void doSolution() {
		
		// ���̷������� M����ŭ �̴� ����� �� ���(����)
		int pickNum = M;
		int index=0;
		int count = 0;
		arr = new int[pickNum];
		pickVirus(index, count, pickNum);
		
	}

	private static void pickVirus(int index, int count, int pickNum) {
		if(count == pickNum) {
			
			// �� ����� ���� ������� ���̷��� Ȱ��ȭ ��Ű��
			if(spreadVirus()) {
				result = Math.min(result, Time);				
			}
			
			return;
		}
		
		for(int i=index; i<virusList.size(); ++i) {
			arr[count] = i;
			pickVirus(i+1, count+1, pickNum);
		}
	}

	private static boolean spreadVirus() {
		Queue<Position_17142> queue = new LinkedList<>();
		boolean[][] checked = new boolean[N][N];
		
		for(int idx : arr) {
			int y = virusList.get(idx).y;
			int x = virusList.get(idx).x;
			
			queue.offer(new Position_17142(y, x));
			checked[y][x] = true;
		}
		
		Time = 0;
		while(!queue.isEmpty()) {
			
			Time++;
			int queueSize = queue.size();
			
			for(int s=0; s<queueSize; ++s) {
				Position_17142 q = queue.poll();
				
				for(int d=0; d<4; ++d) {
					int moveY = q.y + directions[0][d];
					int moveX = q.x + directions[1][d];
					
					// ���� �ȿ� ������, �湮���� ����, ���� �ƴ϶��
					if(isRanged(moveY, moveX) && !checked[moveY][moveX] && Map[moveY][moveX] != 1) {
						
						queue.offer(new Position_17142(moveY, moveX));
						checked[moveY][moveX] = true;								
					}
				}				
			}
			
			// 1�ʰ� �带������ ���̷����� ��� �������� Ȯ��
			boolean mark = true;
			for(int i=0; i<N; ++i) {
				for(int j=0; j<N; ++j) {
					
					// ��ĭ �κ��� �� �����ٸ�
					if(Map[i][j] ==0 && !checked[i][j]) {
						mark = false;
						break;
					}
				}
				if(!mark) break;
			}
			
			// ��� �����ٸ�
			if(mark) return true;			
		}
		
		return false;
	}

	private static boolean isRanged(int y, int x) {
		if(y>=0 && y<N && x>=0 && x<N) return true;
		return false;
	}
}
