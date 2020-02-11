//홈 방범 서비스
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Pos_2117 {
	public int y,x;
	public int step;
	
	public Pos_2117(int y, int x, int step) {
		super();
		this.y = y;
		this.x = x;
		this.step = step;
	}	
}

public class Problem_2117 {
	
	static int N;		//도시 수
	static int M;		//하나의 집이 지불할 수 있는 비용
	static int Cost;	//운영비용
	static int[][] map;
	
	static int[][] directions = {
			{-1, 0, 1, 0},
			{0, 1, 0, -1}
	};		
	static int result;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int testCase= Integer.parseInt(br.readLine());
		int t=1;
		while(t<=testCase) {
			String config = br.readLine();
			st = new StringTokenizer(config, " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			map = new int[N][N];
			
			for(int i=0; i<N; ++i) {
				String input = br.readLine();
				st = new StringTokenizer(input, " ");
				for(int j=0; j<N; ++j) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
						
			//기준점 이동
			result = 0;
			for(int i=0; i<N; ++i) {
				for(int j=0; j<N; ++j) {
					doProcess(i, j);
				}
			}
									
			System.out.println("#" + t + " " + result);
			t++;
		}
	}

	private static void doProcess(int y, int x) {
		
		//k를 1<= <N+1까지 경우의 수 고려		
		for(int k=1; k<=N+1; ++k) {
			Cost = (k * k) + ((k-1) * (k-1));
			int homeCnt = doBfs(y, x, k);	
			int profit = (homeCnt * M) - Cost;
			
			if(profit >= 0 && homeCnt > result) {							
				result = homeCnt;
			}		
		}		
	}

	private static int doBfs(int y, int x, int k) {
		Queue<Pos_2117> queue = new LinkedList<Pos_2117>();
		boolean[][] checked = new boolean[N][N];
		
		queue.offer(new Pos_2117(y, x, 1));
		checked[y][x] = true;
		
		//시작 위치에 집이 있다면 추가
		int homeCnt = 0;
		if(map[y][x] == 1) {
			homeCnt++;
		}
		
		while(!queue.isEmpty()) {
			Pos_2117 q = queue.poll();			
			
			for(int d=0; d<4; ++d) {
				int moveY = q.y + directions[0][d];
				int moveX = q.x + directions[1][d];
				
				if(q.step == k) {
					continue;
				}
				
				if(isRanged(moveY, moveX) && !checked[moveY][moveX]) {
					if(map[moveY][moveX] == 1) {
						homeCnt++;
					}
					queue.offer(new Pos_2117(moveY, moveX, q.step+1));
					checked[moveY][moveX] = true;
				}
			}				
		}
		
		return homeCnt;
	}

	private static boolean isRanged(int y, int x) {
		if(y>=0 && y<N && x>=0 && x<N) return true;
		return false;
	}

}
