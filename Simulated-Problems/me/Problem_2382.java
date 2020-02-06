//�̻��� �ݸ�
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class MicroInfo_2382 {
	public int y, x;
	public int microSize;		//������(�� ����Ŭ��)
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

	static MicroInfo_2382[][] map; // ��
	static Queue<MicroInfo_2382> queue; // ť
	static int N; // �� ������
	static int K; // �̻��� ���� ����
	static int M; // �ݸ� �ð�

	// �����¿�
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

			// ���� ���� ���� �� map�� ���� ��ġ����
			for (int i = 1; i <= K; ++i) {
				input = br.readLine();
				st = new StringTokenizer(input, " ");

				int y = Integer.parseInt(st.nextToken()); 			// ��
				int x = Integer.parseInt(st.nextToken()); 			// ��
				int microSize = Integer.parseInt(st.nextToken()); 	// �̻��� ��
				int dir = Integer.parseInt(st.nextToken()); 		// ����

				MicroInfo_2382 micro = new MicroInfo_2382(y, x, microSize, dir);
				micro.microOrigin = microSize;						
				queue.offer(micro);
			}

			// �ݸ� �ð����� ����
			int answer = 0;
			for (int m = 0; m < M; ++m) {
				answer = 0;
				
				while (!queue.isEmpty()) {
					MicroInfo_2382 micro = queue.poll();

					// �̵�
					int moveY = micro.y + directions[0][micro.dir];
					int moveX = micro.x + directions[1][micro.dir];

					// �̵��� ������ �ƹ��͵� ���ٸ� �״�� �̵�
					if (map[moveY][moveX] == null) {
						
						// �����ڸ��� ���� ���
						if (isEdged(moveY, moveX)) {
							int dir = micro.dir;
							
							if (dir == 1) dir = 2;
							else if (dir == 2) dir = 1;
							else if (dir == 3) dir = 4;
							else if (dir == 4) dir = 3;
							
							map[moveY][moveX] = new MicroInfo_2382(moveY, moveX, micro.microOrigin/2, dir);
							map[moveY][moveX].microOrigin = micro.microOrigin;
						}

						// ���� ���� ���� ���
						else {
							// �׳� �̵�
							map[moveY][moveX] = new MicroInfo_2382(moveY, moveX, micro.microOrigin, micro.dir);
							map[moveY][moveX].microOrigin = micro.microOrigin;
						}

					}

					// �̵��� ������ �̹� �����ϴ� ���� �ִٸ�
					else {
						
						// �����ڸ��� ���� ��� -> ������ �������� �Ǿ�����
						if (isEdged(moveY, moveX)) {
							map[moveY][moveX].microSize += (micro.microOrigin / 2);
						}

						// ���� ������ ������ ���
						else {
							// �̹� �����ϴ� ���� �̻����� �� ���ٸ� -> ���� ����, ���� ����
							if (map[moveY][moveX].microOrigin > micro.microOrigin) {
								map[moveY][moveX].microSize += micro.microSize;
							}

							// ���ٸ� -> ���� ����, �� ����
							else {
								map[moveY][moveX].dir = micro.dir;
								map[moveY][moveX].microOrigin = micro.microOrigin;
								map[moveY][moveX].microSize += micro.microOrigin;
							}
						}
					}										
				}	
				
				// map�� ���鼭 �����ϴ� ���� �ٽ� ť�� ����
				queue = new LinkedList<>();
				for (int i = 0; i < N; ++i) {
					for (int j = 0; j < N; ++j) {
						if (map[i][j] != null) {							
							answer += map[i][j].microSize;
							
							//�ش� ��ġ�� ������ ���� �̻����� �������� ����
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
