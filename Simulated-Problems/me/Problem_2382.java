//미생물 격리
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class MicroInfo_2382 {
	public int y, x;
	public int microSize;		//누적합(한 사이클당)
	public int microOrigin;
	public int dir;

	public MicroInfo_2382(int y, int x, int microSize, int dir) {
		super();
		this.y = y;
		this.x = x;
		this.microSize = microSize;
		this.dir = dir;
	}
}

public class Problem_2382 {

	static MicroInfo_2382[][] map; // 맵
	static Queue<MicroInfo_2382> queue; // 큐
	static int N; // 맵 사이즈
	static int K; // 미생물 군집 개수
	static int M; // 격리 시간

	// 상하좌우
	static int[][] directions = { { 0, -1, 1, 0, 0 }, { 0, 0, 0, -1, 1 } };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int testCase = Integer.parseInt(br.readLine());
		int t = 1;
		while (t <= testCase) {
			String input = br.readLine();
			st = new StringTokenizer(input, " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			map = new MicroInfo_2382[N][N];
			queue = new LinkedList<>();

			// 군집 정보 저장 및 map에 군집 위치설정
			for (int i = 1; i <= K; ++i) {
				input = br.readLine();
				st = new StringTokenizer(input, " ");

				int y = Integer.parseInt(st.nextToken()); 			// 행
				int x = Integer.parseInt(st.nextToken()); 			// 열
				int microSize = Integer.parseInt(st.nextToken()); 	// 미생물 수
				int dir = Integer.parseInt(st.nextToken()); 		// 방향

				MicroInfo_2382 micro = new MicroInfo_2382(y, x, microSize, dir);
				micro.microOrigin = microSize;						
				queue.offer(micro);
			}

			// 격리 시간동안 진행
			int answer = 0;
			for (int m = 0; m < M; ++m) {
				answer = 0;
				
				while (!queue.isEmpty()) {
					MicroInfo_2382 micro = queue.poll();

					// 이동
					int moveY = micro.y + directions[0][micro.dir];
					int moveX = micro.x + directions[1][micro.dir];

					// 이동한 구간에 아무것도 없다면 그대로 이동
					if (map[moveY][moveX] == null) {
						
						// 가장자리로 가는 경우
						if (isEdged(moveY, moveX)) {
							int dir = micro.dir;
							
							if (dir == 1) dir = 2;
							else if (dir == 2) dir = 1;
							else if (dir == 3) dir = 4;
							else if (dir == 4) dir = 3;
							
							map[moveY][moveX] = new MicroInfo_2382(moveY, moveX, micro.microOrigin/2, dir);
							map[moveY][moveX].microOrigin = micro.microOrigin;
						}

						// 범위 내로 가는 경우
						else {
							// 그냥 이동
							map[moveY][moveX] = new MicroInfo_2382(moveY, moveX, micro.microOrigin, micro.dir);
							map[moveY][moveX].microOrigin = micro.microOrigin;
						}

					}

					// 이동한 구간에 이미 존재하는 군이 있다면
					else {
						
						// 가장자리로 가는 경우 -> 방향은 같아지게 되어있음
						if (isEdged(moveY, moveX)) {
							map[moveY][moveX].microSize += (micro.microOrigin / 2);
						}

						// 범위 내에서 만나는 경우
						else {
							// 이미 존재하는 군의 미생물이 더 많다면 -> 방향 유지, 값만 누적
							if (map[moveY][moveX].microOrigin > micro.microOrigin) {
								map[moveY][moveX].microSize += micro.microSize;
							}

							// 적다면 -> 방향 변경, 값 누적
							else {
								map[moveY][moveX].dir = micro.dir;
								map[moveY][moveX].microOrigin = micro.microOrigin;
								map[moveY][moveX].microSize += micro.microOrigin;
							}
						}
					}										
				}	
				
				// map을 돌면서 존재하는 곳을 다시 큐에 삽입
				queue = new LinkedList<>();
				for (int i = 0; i < N; ++i) {
					for (int j = 0; j < N; ++j) {
						if (map[i][j] != null) {							
							answer += map[i][j].microSize;
							
							//해당 위치에 누적된 합을 미생물의 원본으로 갱신
							map[i][j].microOrigin = map[i][j].microSize;
							queue.offer(map[i][j]);
						}
					}
				}				
				
				map = new MicroInfo_2382[N][N];
			}						

			System.out.println("#" + t + " " + answer);
			t++;
		}
	}

	private static boolean isEdged(int y, int x) {
		if (y == 0 || y == N - 1 || x == 0 || x == N - 1)
			return true;

		return false;
	}

}
