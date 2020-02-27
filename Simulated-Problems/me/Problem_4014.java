//활주로 건설
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Problem_4014 {

	static int N; // map size
	static int X; // 경사로 가로 길이
	static final int H = 1; // 경사로 높이(1로 고정)
	static int[][] map;

	// 아래, 오른
	static int[] dy = { 1, 0 };
	static int[] dx = { 0, 1 };

	static boolean possible;
	static int answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int testCase = Integer.parseInt(br.readLine());
		int t = 1;
		while (t <= testCase) {
			String input = br.readLine();
			st = new StringTokenizer(input, " ");
			N = Integer.parseInt(st.nextToken());
			X = Integer.parseInt(st.nextToken());
			map = new int[N][N];

			// map 입력
			for (int i = 0; i < N; ++i) {
				input = br.readLine();
				st = new StringTokenizer(input, " ");
				for (int j = 0; j < N; ++j) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			answer = 0;
			doSolution();

			System.out.println("#" + t + " " + answer);
			t++;
		}
	}

	private static void doSolution() {
		
		//행과 열 재귀 진행
		for(int i=0; i<N; ++i) {	
			int cnt = 1;
			int dir = 1;
			possible = true;					
			doProcess(i, 0, cnt, dir);
			if(possible) answer++;
									
			dir = 0;
			possible = true;
			doProcess(0, i, cnt, dir);			
			if(possible) answer++;			
		}	
		
	}

	private static void doProcess(int y, int x, int cnt, int dir) {				
		int moveY = y + dy[dir];
		int moveX = x + dx[dir];			
		
		if(!isRanged(moveY, moveX)) {
			if(cnt >= 0) possible = true;
			else possible = false;
			
			return;
		}

		// 같은 높이인 경우
		if (map[moveY][moveX] - map[y][x] == 0) cnt++;		

		// 다음 칸이 1칸 높은 경우 & 경사로 설치가 가능하면 cnt 초기화
		else if (map[moveY][moveX] - map[y][x] == H && cnt >= X) {
			cnt = 1;
		}

		// 다음 칸이 1칸 낮은 경우 -> 음수로 카운팅
		// 다음 재귀부터 높이가 같다면 cnt++을 하다보면 0이 된 순간 경사로 설치 가능 여부를 구할 수 있음
		else if (map[moveY][moveX] - map[y][x] == -H && cnt >= 0) cnt = -X + 1;	

		else {
			possible = false;
			return;
		}

		doProcess(moveY, moveX, cnt, dir);
	}

	private static boolean isRanged(int y, int x) {
		if(x>=0 && x<N && y>=0 && y<N) return true;
		return false;
	}

}
