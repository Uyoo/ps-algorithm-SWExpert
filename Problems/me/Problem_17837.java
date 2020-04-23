// ���ο� ���� 2
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

	// �ð����
	static int[][] directions = { { -1, 0, 1, 0 }, { 0, 1, 0, -1 } };

	// ����, ��, ��, �Ʒ�(1~4)
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

		// ���� ������ŭ �� ����Ŭ�� ������
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
				
				// ���� ���� 4 �̻��� ������ �׿��ִٸ� ����
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

		// �ش� ������ �ٸ� ���鵵 �����Ѵٸ� ����Ʈ�� ���� ���(���� �̵��ؾ��ϴϱ�)
		LinkedList<Knight> list = new LinkedList<>();
		int idx = Map[q.y][q.x].queue.indexOf(q);

		// ���� ĭ�� �����ϴ� ���� ������ ����
		for (int i = idx; i < Map[q.y][q.x].queue.size(); ++i) {
			Knight tmp = Map[q.y][q.x].queue.get(i);
			list.add(tmp);
		}

		for (int i = 0; i < list.size(); ++i) {
			Map[q.y][q.x].queue.remove(list.get(i));
		}

		// �̵� ������ ���ǵ� ���
		int moveY = q.y + directions[0][dir[q.dir]];
		int moveX = q.x + directions[1][dir[q.dir]];

		// �̵��Ϸ��� ĭ�� ������ ����ų� �Ķ��� ĭ�� ���
		if (!isRanged(moveY, moveX) || Map[moveY][moveX].value == 2) {

			// ������ �ݴ�� �ٲٰ� �� ĭ �̵�
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

			// ���� �̵��� ��ġ�� ������ ����ų�, �Ķ�ĭ�̶��
			if (!isRanged(moveY, moveX) || Map[moveY][moveX].value == 2) {

				// ����ĭ�� �ӹ���(������ �ٲ� ���� ����)
				for (Knight tmp : list) {
					Map[q.y][q.x].queue.add(tmp);
					tmp.y = q.y;
					tmp.x = q.x;
				}
			}

			// ���ĭ or ����ĭ�̶�� �̵�
			else {
				
				// ��� ĭ�̶�� -> �̵�
				if(Map[moveY][moveX].value == 0) {
					for (Knight tmp : list) {
						Map[moveY][moveX].queue.add(tmp);
						tmp.y = moveY;
						tmp.x = moveX;
					}
				}
				
				// ���� ĭ�̶��
				else if(Map[moveY][moveX].value == 1) {
					
					// ������ ������ ������ �ְ� �̵�
					for (int i = list.size() - 1; i >= 0; --i) {
						Map[moveY][moveX].queue.add(list.get(i));
						list.get(i).y = moveY;
						list.get(i).x = moveX;
					}
				}
			}
		}

		// �̵��Ϸ��� ĭ�� ����� ���
		else if (Map[moveY][moveX].value == 0) {

			// �̵�
			for (Knight tmp : list) {
				Map[moveY][moveX].queue.add(tmp);
				tmp.y = moveY;
				tmp.x = moveX;
			}
		}

		// �������� ���
		else if (Map[moveY][moveX].value == 1) {

			// ������ ������ ������ �ְ� �̵�
			for (int i = list.size() - 1; i >= 0; --i) {
				Map[moveY][moveX].queue.add(list.get(i));
				list.get(i).y = moveY;
				list.get(i).x = moveX;
			}
		}

		// �Ķ����� ���
		else if (Map[moveY][moveX].value == 2) {

			// ������ �ݴ�� �ٲٰ� �� ĭ �̵�
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

			// ���� �̵��� ��ġ�� ������ ����ų�, �Ķ�ĭ�̶��
			if (!isRanged(moveY, moveX) || Map[moveY][moveX].value == 2) {

				// ����ĭ�� �ӹ���(������ �ٲ� ���� ����)
				for (Knight tmp : list) {
					Map[q.y][q.x].queue.add(tmp);
					tmp.y = q.y;
					tmp.x = q.x;
				}
			}

			// ���ĭ�̶�� �̵�
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
