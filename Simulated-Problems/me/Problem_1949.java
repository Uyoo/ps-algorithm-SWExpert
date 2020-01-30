package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Mountain_1949 {
	public int y, x;
	public int step;

	public Mountain_1949(int y, int x, int step) {
		super();
		this.y = y;
		this.x = x;
		this.step = step;
	}
}

public class Problem_1949 {

	static int N;
	static int K;
	static int[][] map;

//	static int[] dy = {-1, 0, 1, 0};
//	static int[] dx = {0, 1, 0, -1};
	static int[][] dir = { { -1, 0, 1, 0 }, { 0, 1, 0, -1 } };
	static boolean[][] checked;

	static ArrayList<Mountain_1949> topPosition;
	static int answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int testCase = Integer.parseInt(br.readLine());
		int t = 1;
		while (t <= testCase) {
			String config = br.readLine();
			st = new StringTokenizer(config, " ");
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			map = new int[N][N];

			// map 받기
			topPosition = new ArrayList<>();
			int max = 0;
			for (int i = 0; i < N; ++i) {
				String input = br.readLine();
				st = new StringTokenizer(input, " ");
				for (int j = 0; j < N; ++j) {
					map[i][j] = Integer.parseInt(st.nextToken());
					if (map[i][j] > max) {
						topPosition = new ArrayList<>();
						topPosition.add(new Mountain_1949(i, j, 1));
						max = map[i][j];
					} else if (map[i][j] == max)
						topPosition.add(new Mountain_1949(i, j, 1));
				}
			}

			checked = new boolean[N][N];
			answer = 0;
			for (Mountain_1949 pos : topPosition) {
				int k = K;

				checked[pos.y][pos.x] = true;
				doSolution(pos, k);
				checked[pos.y][pos.x] = false;
			}

			System.out.println("#" + t + " " + answer);
			t++;
		}

	}

	private static void doSolution(Mountain_1949 pos, int k) {
		for (int i = 0; i < 4; ++i) {
			int moveY = pos.y + dir[0][i];
			int moveX = pos.x + dir[1][i];

			if (isRanged(moveY, moveX) && !checked[moveY][moveX]) {

				// 현재 위치의 높이 > 움직일 위치의 높이 -> 이동가능
				if (map[pos.y][pos.x] > map[moveY][moveX]) {
					checked[moveY][moveX] = true;
					doSolution(new Mountain_1949(moveY, moveX, pos.step + 1), k);
					checked[moveY][moveX] = false;
				}

				// 현재 위치의 높이 < 움직일 위치의 높이 -> k 사용해서 가는 경우 or 사용 안해서 안가는 경우(2가지)
				else {

					// k 사용해서 가는 경우 (단, k를 사용해서 갈 수 있는지 먼저 판별)
					for (int dig = 1; dig <= k; ++dig) {
						if ((map[moveY][moveX] - dig) < map[pos.y][pos.x]) {
							checked[moveY][moveX] = true;
							map[moveY][moveX] -= dig;

							doSolution(new Mountain_1949(moveY, moveX, pos.step + 1), 0);

							map[moveY][moveX] += dig;
							checked[moveY][moveX] = false;
						}					
					}
				}
			}
		}
		answer = Math.max(answer, pos.step);
	}

	private static boolean isRanged(int y, int x) {
		if (y >= 0 && y < N && x >= 0 && x < N)
			return true;
		return false;
	}

}
