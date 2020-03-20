//벽돌깨기
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class PositionInfo_5656 {
	public int y,x;
	public int limit;

	public PositionInfo_5656(int y, int x, int limit) {
		super();
		this.y = y;
		this.x = x;
		this.limit = limit;
	}	
}

public class Problem_5656 {
	
	static int N, W, H;	//구슬 개수, 열, 행
	static int[][] map;
	static int[] ballPosition;
	
	static int[][] directions = {
			{-1, 0, 1, 0},
			{0, 1, 0, -1}
	};
	
	static Queue<PositionInfo_5656> queue;
	static int answer;
	static int remainBrick;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int testCase = Integer.parseInt(br.readLine());
		int t=1;
		while(t<=testCase) {
			String input = br.readLine();
			st = new StringTokenizer(input, " ");
			N = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());			
			map = new int[H][W];
			ballPosition = new int[N];
			
			int cnt=0;
			for(int i=0; i<H; ++i) {
				input = br.readLine();
				st = new StringTokenizer(input, " ");
				for(int j=0; j<W; ++j) {
					map[i][j] = Integer.parseInt(st.nextToken());
					if(map[i][j] > 0) cnt++;
				}
			}
						
			answer = cnt;			
			doSolution();
			
			System.out.println("#" + t + " " + answer);
			t++;
		}

	}

	private static void doSolution() {
		//공을 놓을 수 있는 경우의 수 탐색(중복순열)		
		int count=0;		
		doProcess(count);		
	}

	private static void doProcess(int count) {		
		if(count == N) {
			//벽돌깨기 진행
			destroyBicks(); 
			answer = Math.min(answer, remainBrick);
			
			return;			
		}		
		
		for(int j=0; j<W; ++j) {
			ballPosition[count] = j;
			doProcess(count+1);
		}
		
	}

	private static void destroyBicks() {
		int[][] tmp = new int[H][W];
		for(int i=0; i<H; ++i) {
			for(int j=0; j<W; ++j) {
				tmp[i][j] = map[i][j];
			}
		}
		
		int n=0;
		remainBrick = 0;
		while(n<N) {
			int position = ballPosition[n];
			
			//맨 위에서부터 만나는 벽돌 찾기
			int row = findBrick(position, tmp);
			if(row != -1) {
				int limit = tmp[row][position];
				
				//벽돌깨기
				play(row, position, tmp, limit);								
				
				//벽돌 정리
				relocationBricks(tmp);						
			}			
			
			n++;
		}
		
		//남은 벽돌 개수 
		countBricks(tmp);					
	}
	
	private static void countBricks(int[][] tmp) {
		for(int i=0; i<H; ++i) {
			for(int j=0; j<W; ++j) {				
				if(tmp[i][j] > 0) remainBrick++;
			}
		}		
	}

	private static void relocationBricks(int[][] tmp) {			
		for(int j=0; j<W; ++j) {
			ArrayList<Integer> list = new ArrayList<>();
			for(int i=H-1; i>=0; --i) {
				if(tmp[i][j] > 0) {
					list.add(tmp[i][j]);
					tmp[i][j]=0;
				}
			}						
					
			int idx=H-1;
			for(int value : list) {
				tmp[idx--][j] = value;				
			}
		}	
	}

	private static void play(int Y, int X, int[][] tmp, int limit) {		
		queue = new LinkedList<>();
		queue.offer(new PositionInfo_5656(Y, X, limit));
		tmp[Y][X] = 0;
		
		while(!queue.isEmpty()) {
			PositionInfo_5656 q = queue.poll();			
			
			for(int d=0; d<4; ++d) {			
				for(int a=0; a<q.limit; ++a) {
					int moveY = q.y + directions[0][d]*a;
					int moveX = q.x + directions[1][d]*a;
					
					if(isRanged(moveY, moveX) && tmp[moveY][moveX] > 0){
						queue.offer(new PositionInfo_5656(moveY, moveX, tmp[moveY][moveX]));
						tmp[moveY][moveX] = 0;
					}
				}
			}
		}								
	}

	private static int findBrick(int position, int[][] tmp) {		
		for(int i=0; i<H; ++i) {
			if(tmp[i][position] > 0) return i;		
		}		
		return -1;
	}

	private static boolean isRanged(int y, int x) {
		if(y>=0 && y<H && x>=0 && x<W) return true;
		return false;
	}
}
