//Ż�ֹ� �˰�
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
	static int Entrance_Y;	//��Ȧ�Ѳ� ��
	static int Entrance_X;	//��Ȧ�Ѳ� ��
	static int Time;	//�ҿ�ð�
	static int[][] map;
	static boolean[][] checked;
	
	static int[][] directions = {
			{-1, 0, 1, 0},
			{0, 1, 0, -1}
	};
	
	static int[][] structures = {
			//������ (1~7)
			{}, 			//������ 0�� �ƹ��͵� �ƴϱ⋚����
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
			
			//map �Է�
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
				
				//���� ��ġ���� �̵� ������ �������θ� ���� ��ġ Ž��
				int structureIdx = map[q.y][q.x];
				for(int dir : structures[structureIdx]) {
					int moveY = q.y + directions[0][dir];
					int moveX = q.x + directions[1][dir];
					
					if(isRanged(moveY, moveX) && !checked[moveY][moveX] && map[moveY][moveX] > 0) {
						
						//������ ���� �������� ������ �������� �Ǵ�
						int adjStructureIdx = map[moveY][moveX];
						boolean possible = false;
						for(int adjDir : structures[adjStructureIdx]) {
							
							//�ݴ� ������ �����ϴ��� Ȯ��
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
