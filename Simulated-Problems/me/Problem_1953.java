//탈주범 검거
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Position_1953 {
	public int y,x;
	
	public Position_1953(int y, int x) {
		super();
		this.y = y;
		this.x = x;
	}	
}

public class Problem_1953 {
	static int N,M;
	static int Entrance_Y;	//맨홀뚜껑 행
	static int Entrance_X;	//맨홀뚜껑 열
	static int Time;	//소요시간
	static int[][] map;
	static boolean[][] checked;
	
	static int[][] directions = {
			{-1, 0, 1, 0},
			{0, 1, 0, -1}
	};
	
	static int[][] structures = {
			//구조물 (1~7)
			{}, 			//구조물 0은 아무것도 아니기떄문에
			{0, 1, 2, 3},
			{0, 2},
			{1, 3},
			{0, 1},
			{1, 2},
			{2, 3},
			{0, 3}
	};	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int testCase = Integer.parseInt(br.readLine());
		int t=1;
		while(t <= testCase) {
			String input = br.readLine();
			st = new StringTokenizer(input, " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			Entrance_Y = Integer.parseInt(st.nextToken());
			Entrance_X = Integer.parseInt(st.nextToken());
			Time = Integer.parseInt(st.nextToken());
			
			//map 입력
			map = new int[N][M];			
			for(int i=0; i<N; ++i) {
				input = br.readLine();
				st = new StringTokenizer(input, " ");
				for(int j=0; j<M; ++j) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			int answer = doSolution();									
			System.out.println("#" + t + " " + answer);
			t++;
		}
	}


	private static int doSolution() {
		Queue<Position_1953> queue = new LinkedList<Position_1953>();
		checked = new boolean[N][M];
		
		queue.offer(new Position_1953(Entrance_Y, Entrance_X));
		checked[Entrance_Y][Entrance_X] = true;
		
		int count=1;
		int cycle=1;
		while(!queue.isEmpty()) {
			int size = queue.size();
			if(cycle == Time) break;
			
			for(int s=0; s<size; ++s) {
				Position_1953 q = queue.poll();
				
				//현재 위치에서 이동 가능한 방향으로만 인접 위치 탐색
				int structureIdx = map[q.y][q.x];
				for(int dir : structures[structureIdx]) {
					int moveY = q.y + directions[0][dir];
					int moveX = q.x + directions[1][dir];
					
					if(isRanged(moveY, moveX) && !checked[moveY][moveX] && map[moveY][moveX] > 0) {
						
						//인접한 곳의 구조물도 연결이 가능한지 판단
						int adjStructureIdx = map[moveY][moveX];
						boolean possible = false;
						for(int adjDir : structures[adjStructureIdx]) {
							
							//반대 방향이 존재하는지 확인
							if((dir + 2) % 4 == adjDir) {
								possible = true;
								break;
							}
						}
						
						if(possible) {
							queue.offer(new Position_1953(moveY, moveX));
							checked[moveY][moveX] = true;
							count++;
						}					
					}
				}
			}	
			cycle++;
		}
		
		return count;
	}

	private static boolean isRanged(int y, int x) {
		if(y>=0 && y<N && x>=0 && x<M) return true;
		return false;
	}

}
