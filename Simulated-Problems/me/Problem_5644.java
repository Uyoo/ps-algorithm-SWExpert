//무선충전
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Problem_5644 {
	
	static int N = 10;
	static int[][][] AP;	
	static int M;		//총 이동시간
	static int APSize;	//무선충전기 개수
	static int[][] operations;	//사용자(A,B)의 명령어
	
	static int[][] directions = {
			{0, -1, 0, 1, 0},
			{0, 0, 1, 0, -1}
	};
	
	static Queue<User_Info> userA;
	static Queue<User_Info> userB;
	static int result;
	
	static class User_Info {
		public int y,x;

		public User_Info(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}	
	}
	
	static class AP_INFO {
		public int y,x;
		public int coverage;
		public int performance;
		
		public AP_INFO(int y, int x, int coverage, int performance) {
			super();
			this.y = y;
			this.x = x;
			this.coverage = coverage;
			this.performance = performance;
		}		
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int testCase = Integer.parseInt(br.readLine());
		int t=1;
		while(t<=testCase) {
			String config = br.readLine();
			st = new StringTokenizer(config, " ");
			M = Integer.parseInt(st.nextToken());
			APSize = Integer.parseInt(st.nextToken());			
			AP = new int[APSize][N+1][N+1];

			//A와 B의 명령어 입력
			operations = new int[2][M+1];
			for(int a=0; a<2; ++a) {
				String input = br.readLine();
				st = new StringTokenizer(input, " ");
				
				int idx=1;
				while(st.hasMoreTokens()) {
					operations[a][idx++] = Integer.parseInt(st.nextToken());
				}
			}
			
			//무선 충전기 입력 받기
			for(int a=0; a<APSize; ++a) {
				String input = br.readLine();
				st = new StringTokenizer(input, " ");
				
				//좌표, 커버, 파워
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int coverage = Integer.parseInt(st.nextToken());
				int performance = Integer.parseInt(st.nextToken());
				
				//해당 무선 충전기 bfs
				AP_INFO apInfo = new AP_INFO(y, x, coverage, performance);
				doBfs(apInfo, a);
			}						
			
			//사용자(A, B) 한칸씩 이동
			userA = new LinkedList<>();
			userB = new LinkedList<>();
			userA.offer(new User_Info(1, 1));
			userB.offer(new User_Info(10, 10));
			
			result = 0;
			for(int j=0; j<=M; ++j) {
				int operA = operations[0][j];
				int operB = operations[1][j];
				
				moveUser(operA, operB);				
			}
			
			
			System.out.println("#" + t + " " + result);
			t++;
		}

	}

	private static void moveUser(int operA, int operB) {
		User_Info aPos = userA.poll();	//현재 A의 위치
		User_Info bPos = userB.poll();
		
		//A의 움직일 위치
		aPos.y = aPos.y + directions[0][operA];
		aPos.x = aPos.x + directions[1][operA];
		
		//B의 움직일 위치
		bPos.y = bPos.y + directions[0][operB];
		bPos.x = bPos.x + directions[1][operB];					
		
		int max = 0;
		for(int a=0; a<APSize; ++a) {
			for(int b=0; b<APSize; ++b) {
				if(AP[a][aPos.y][aPos.x] > 0 && AP[b][bPos.y][bPos.x] > 0) {
					//a == b라면 -> 절반으로 분할
					if(a == b) {
						max = Math.max(max, AP[a][aPos.y][aPos.x] / 2);
					}
					
					//다르다면 두개의 합으로 갱신
					else {
						max = Math.max(max, AP[a][aPos.y][aPos.x] + AP[b][bPos.y][bPos.x]);
					}
					
				}	
				
				else if(AP[a][aPos.y][aPos.x] > 0 || AP[b][bPos.y][bPos.x] > 0) {
					max = Math.max(max, AP[a][aPos.y][aPos.x] + AP[b][bPos.y][bPos.x]);
				}
				
				//한쪽만 무선 충전기 범위에 속한 경우 or 둘다 무선 충전기 범위에 없는 경우
				else if(AP[a][aPos.y][aPos.x] == 0 && AP[b][bPos.y][bPos.x] == 0) {
					max = Math.max(max, AP[a][aPos.y][aPos.x] + AP[b][bPos.y][bPos.x]);
				}
			}
		}
		
		result += max;
		userA.offer(new User_Info(aPos.y, aPos.x));
		userB.offer(new User_Info(bPos.y, bPos.x));
	}

	private static void doBfs(AP_INFO apInfo, int apIdx) {		
		Queue<AP_INFO> queue = new LinkedList<>();		
		queue.offer(apInfo);
		AP[apIdx][apInfo.y][apInfo.x] = apInfo.performance;
		
		int cover = apInfo.coverage;
		int cycle = 1;
		while(!queue.isEmpty()) {
			if(cycle > cover) break;
			
			int size = queue.size();			
			for(int i=0; i<size; ++i) {
				AP_INFO q = queue.poll();
				
				for(int d=1; d<=4; ++d) {
					int moveY = q.y + directions[0][d];
					int moveX = q.x + directions[1][d];
					
					if(isRanged(moveY, moveX) && AP[apIdx][moveY][moveX] == 0) {
						queue.offer(new AP_INFO(moveY, moveX, cover, q.performance));
						AP[apIdx][moveY][moveX] = q.performance;
					}
				}
			}			
			cycle++;				
		}
	}

	private static boolean isRanged(int y, int x) {
		if(y>=1 && y<=N && x>=1 && x<=N) return true;
		return false;
	}

}
