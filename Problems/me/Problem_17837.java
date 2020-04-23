// 새로운 게임 2
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class MapInfo_17837 {

	public int value;
	public LinkedList<Knight> queue;

	public MapInfo_17837(int value) {
		this.value = value;
		this.queue = new LinkedList<>();
	}
}

class Knight {

	public int y, x;
	public int dir;

	public Knight(int y, int x, int dir) {
		super();
		this.y = y;
		this.x = x;
		this.dir = dir;
	}
}

public class Problem_17837 {

	static int N;
	static MapInfo_17837[][] Map;
	static int K;
	static LinkedList<Knight> KnightQueue;

	// 시계방향
	static int[][] directions = { { -1, 0, 1, 0 }, { 0, 1, 0, -1 } };

	// 오른, 왼, 위, 아래(1~4)
	static int[] dir = { -1, 1, 3, 0, 2 };

	static int result;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		String input = br.readLine();
		st = new StringTokenizer(input, " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		Map = new MapInfo_17837[N + 1][N + 1];

		// input map
		for (int i = 1; i <= N; ++i) {
			input = br.readLine();
			st = new StringTokenizer(input, " ");

			for (int j = 1; j <= N; ++j) {
				Map[i][j] = new MapInfo_17837(Integer.parseInt(st.nextToken()));
			}
		}

		// input Knight
		KnightQueue = new LinkedList<>();
		for (int i = 0; i < K; ++i) {
			input = br.readLine();
			st = new StringTokenizer(input, " ");
			int y = Integer.parseInt(st.nextToken());
			int x = Integer.parseInt(st.nextToken());
			int dir = Integer.parseInt(st.nextToken());

			Knight knight = new Knight(y, x, dir);
			KnightQueue.offer(knight);
			Map[y][x].queue.add(knight);
		}

		result = 0;
		doSolution();
		System.out.println(result);
	}

	private static void doSolution() {

		// 말의 개수만큼 한 사이클씩 돌리기
		int cycle = 1;
		while (true) {

			if (cycle > 1000) {
				result = -1;
				return;
			}

			int size = KnightQueue.size();
			for (int s = 0; s < size; ++s) {
				Knight q = KnightQueue.get(s);

				doProcess(q);
				
				// 진행 도중 4 이상의 말들이 쌓여있다면 종료
				boolean find = false;
				for (int i = 0; i < KnightQueue.size(); ++i) {
					if(Map[KnightQueue.get(i).y][KnightQueue.get(i).x].queue.size() >= 4) {
						find = true;
						break;
					}
				}

				if (find) {
					result = cycle;
					return;
				}
			}			

			cycle++;
		}
	}

	private static void doProcess(Knight q) {

		// 해당 말위에 다른 말들도 존재한다면 리스트에 따로 담기(같이 이동해야하니까)
		LinkedList<Knight> list = new LinkedList<>();
		int idx = Map[q.y][q.x].queue.indexOf(q);

		// 현재 칸에 존재하는 말의 정보는 제거
		for (int i = idx; i < Map[q.y][q.x].queue.size(); ++i) {
			Knight tmp = Map[q.y][q.x].queue.get(i);
			list.add(tmp);
		}

		for (int i = 0; i < list.size(); ++i) {
			Map[q.y][q.x].queue.remove(list.get(i));
		}

		// 이동 가능한 조건들 고려
		int moveY = q.y + directions[0][dir[q.dir]];
		int moveX = q.x + directions[1][dir[q.dir]];

		// 이동하려는 칸이 범위를 벗어나거나 파란색 칸인 경우
		if (!isRanged(moveY, moveX) || Map[moveY][moveX].value == 2) {

			// 방향을 반대로 바꾸고 한 칸 이동
			if (q.dir == 1)
				q.dir = 2;
			else if (q.dir == 2)
				q.dir = 1;
			else if (q.dir == 3)
				q.dir = 4;
			else if (q.dir == 4)
				q.dir = 3;

			moveY = q.y + directions[0][dir[q.dir]];
			moveX = q.x + directions[1][dir[q.dir]];

			// 만약 이동할 위치가 범위를 벗어나거나, 파란칸이라면
			if (!isRanged(moveY, moveX) || Map[moveY][moveX].value == 2) {

				// 현재칸에 머문다(방향은 바뀐 상태 유지)
				for (Knight tmp : list) {
					Map[q.y][q.x].queue.add(tmp);
					tmp.y = q.y;
					tmp.x = q.x;
				}
			}

			// 흰색칸 or 빨간칸이라면 이동
			else {
				
				// 흰색 칸이라면 -> 이동
				if(Map[moveY][moveX].value == 0) {
					for (Knight tmp : list) {
						Map[moveY][moveX].queue.add(tmp);
						tmp.y = moveY;
						tmp.x = moveX;
					}
				}
				
				// 빨간 칸이라면
				else if(Map[moveY][moveX].value == 1) {
					
					// 말들의 순서를 뒤집어 주고 이동
					for (int i = list.size() - 1; i >= 0; --i) {
						Map[moveY][moveX].queue.add(list.get(i));
						list.get(i).y = moveY;
						list.get(i).x = moveX;
					}
				}
			}
		}

		// 이동하려는 칸이 흰색인 경우
		else if (Map[moveY][moveX].value == 0) {

			// 이동
			for (Knight tmp : list) {
				Map[moveY][moveX].queue.add(tmp);
				tmp.y = moveY;
				tmp.x = moveX;
			}
		}

		// 빨간색인 경우
		else if (Map[moveY][moveX].value == 1) {

			// 말들의 순서를 뒤집어 주고 이동
			for (int i = list.size() - 1; i >= 0; --i) {
				Map[moveY][moveX].queue.add(list.get(i));
				list.get(i).y = moveY;
				list.get(i).x = moveX;
			}
		}

		// 파란색인 경우
		else if (Map[moveY][moveX].value == 2) {

			// 방향을 반대로 바꾸고 한 칸 이동
			if (q.dir == 1)
				q.dir = 2;
			else if (q.dir == 2)
				q.dir = 1;
			else if (q.dir == 3)
				q.dir = 4;
			else if (q.dir == 4)
				q.dir = 3;

			moveY = q.y + directions[0][dir[q.dir]];
			moveX = q.x + directions[1][dir[q.dir]];

			// 만약 이동할 위치가 범위를 벗어나거나, 파란칸이라면
			if (!isRanged(moveY, moveX) || Map[moveY][moveX].value == 2) {

				// 현재칸에 머문다(방향은 바뀐 상태 유지)
				for (Knight tmp : list) {
					Map[q.y][q.x].queue.add(tmp);
					tmp.y = q.y;
					tmp.x = q.x;
				}
			}

			// 흰색칸이라면 이동
			else {

				for (Knight tmp : list) {
					Map[moveY][moveX].queue.add(tmp);
					tmp.y = moveY;
					tmp.x = moveX;
				}
			}
		}

	}

	private static boolean isRanged(int y, int x) {
		if (y >= 1 && y <= N && x >= 1 && x <= N)
			return true;
		return false;
	}

}
