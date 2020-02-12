//���� �Ҹ� �ùķ��̼�
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Atoms_5648 {
	public int y, x;
	public int dir;
	public int energy;

	public Atoms_5648(int y, int x, int dir, int energy) {
		super();
		this.y = y;
		this.x = x;
		this.dir = dir;
		this.energy = energy;
	}
}

public class Problem_5648 {

	static int N; // ���� ����
	static int[][] count;
	static Atoms_5648[] atoms;
	static int mapSize = 4001;

	// �� �� �� ��
	static int[][] directions = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };

	// ���� ����(�� <-> ��)
	static int[] reverseDir = { 1, 0, 2, 3 };
	static int answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int testCase = Integer.parseInt(br.readLine());
		int t = 1;
		count = new int[mapSize][mapSize];
		Queue<Atoms_5648> queue = new LinkedList<>();
		while (t <= testCase) {
			N = Integer.parseInt(br.readLine());			
			
			//�ʱ�ȭ
			for(int i=0; i<mapSize; ++i) {
				for(int j=0; j<mapSize; ++j) {
					count[i][j] = 0;
				}
			}		
			queue.clear();

			for (int i = 0; i < N; ++i) {
				String input = br.readLine();
				st = new StringTokenizer(input, " ");

				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken());
				int energy = Integer.parseInt(st.nextToken());
				x += 1000;
				y += 1000;

				queue.offer(new Atoms_5648(y * 2, x * 2, dir, energy));
			}

			answer = 0;
			while (!queue.isEmpty()) {
				int size = queue.size();

				for (int s = 0; s < size; ++s) {
					Atoms_5648 q = queue.poll();
					count[q.y][q.x] = 0;	
					
					int dir = reverseDir[q.dir];
					int moveY = q.y + directions[0][dir];
					int moveX = q.x + directions[1][dir];

					// ������ ���ϸ� �̵�
					if (isRanged(moveY, moveX)) {
						q.y = moveY;
						q.x = moveX;
						count[moveY][moveX] += q.energy;
						queue.offer(q);
					}
				}
				
				// �̵��� �� ���Ŀ� ����� ������ ���� ���� ������ ������ ��
				size = queue.size();
				for (int s = 0; s < size; ++s) {
					Atoms_5648 q = queue.poll();
					
					// �������� �ٸ��ٸ� -> �ٸ� ���ڵ� ���յƴٴ� �� -> ���� ����
					if (count[q.y][q.x] != q.energy) {
						answer += count[q.y][q.x];						
						count[q.y][q.x] = 0;
						//q.energy = 0;
					}	
					
					else if (count[q.y][q.x] == q.energy) 
						queue.offer(q);
				}
			}			

			System.out.println("#" + t + " " + answer);
			t++;
		}

	}

	private static boolean isRanged(int y, int x) {
		if (y >= 0 && y < mapSize && x >= 0 && x < mapSize)
			return true;
		return false;
	}

}
