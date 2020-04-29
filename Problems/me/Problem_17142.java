// 연구소 3
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
		
		// 빈칸이 없다면
		if(cnt == 0) System.out.println(0);
		else {
			result = Integer.MAX_VALUE;
			doSolution();
			
			int answer = result == Integer.MAX_VALUE ? -1 : result;
			System.out.println(answer);
		}		
	}

	private static void doSolution() {
		
		// 바이러스들을 M개만큼 뽑는 경우의 수 고려(조합)
		int pickNum = M;
		int index=0;
		int count = 0;
		arr = new int[pickNum];
		pickVirus(index, count, pickNum);
		
	}

	private static void pickVirus(int index, int count, int pickNum) {
		if(count == pickNum) {
			
			// 그 경우의 수를 기반으로 바이러스 활성화 시키기
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
					
					// 범위 안에 들어오고, 방문한적 없고, 벽이 아니라면
					if(isRanged(moveY, moveX) && !checked[moveY][moveX] && Map[moveY][moveX] != 1) {
						
						queue.offer(new Position_17142(moveY, moveX));
						checked[moveY][moveX] = true;								
					}
				}				
			}
			
			// 1초가 흐를때마다 바이러스가 모두 퍼졌는지 확인
			boolean mark = true;
			for(int i=0; i<N; ++i) {
				for(int j=0; j<N; ++j) {
					
					// 빈칸 부분이 다 퍼졌다면
					if(Map[i][j] ==0 && !checked[i][j]) {
						mark = false;
						break;
					}
				}
				if(!mark) break;
			}
			
			// 모두 퍼졌다면
			if(mark) return true;			
		}
		
		return false;
	}

	private static boolean isRanged(int y, int x) {
		if(y>=0 && y<N && x>=0 && x<N) return true;
		return false;
	}
}
