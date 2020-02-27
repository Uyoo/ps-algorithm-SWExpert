//Ȱ�ַ� �Ǽ�
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Problem_4014 {

	static int N; // map size
	static int X; // ���� ���� ����
	static final int H = 1; // ���� ����(1�� ����)
	static int[][] map;

	// �Ʒ�, ����
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

			// map �Է�
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
		
		//��� �� ��� ����
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

		// ���� ������ ���
		if (map[moveY][moveX] - map[y][x] == 0) cnt++;		

		// ���� ĭ�� 1ĭ ���� ��� & ���� ��ġ�� �����ϸ� cnt �ʱ�ȭ
		else if (map[moveY][moveX] - map[y][x] == H && cnt >= X) {
			cnt = 1;
		}

		// ���� ĭ�� 1ĭ ���� ��� -> ������ ī����
		// ���� ��ͺ��� ���̰� ���ٸ� cnt++�� �ϴٺ��� 0�� �� ���� ���� ��ġ ���� ���θ� ���� �� ����
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
