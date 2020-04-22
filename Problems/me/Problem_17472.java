// A�� ���⹮��(�ٸ� ����� 2)
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

class Position_17472 {
	public int y,x;

	public Position_17472(int y, int x) {
		super();
		this.y = y;
		this.x = x;
	}
}

class EdgeInfo_17472 implements Comparable<EdgeInfo_17472> {
	public int nodeIdx;
	public int cost = Integer.MAX_VALUE;
	
	public EdgeInfo_17472(int nodeIdx, int cost) {
		super();
		this.nodeIdx = nodeIdx;
		this.cost = cost;
	}	
	
	@Override
	public int compareTo(EdgeInfo_17472 o) {
		return this.cost - o.cost;		
	}
}

public class Problem_17472 {
	
	static int N, M;
	static int[][] Map;
	static boolean[][] checked;
	static ArrayList<ArrayList<EdgeInfo_17472>> edgeList;
	
	static int[][] directions = {
			{-1, 0, 1, 0},
			{0, 1, 0, -1}
	};
	static int cost;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		String input = br.readLine();
		st = new StringTokenizer(input, " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		Map = new int[N][M];
		
		// input map
		for(int i=0; i<N; ++i) {
			input = br.readLine();
			st = new StringTokenizer(input, " ");
			for(int j=0; j<M; ++j) {
				Map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		cost = 0;		
		doSolution();
		System.out.println(cost);
	}

	private static void doSolution() {
		// ������ �׷�ȭ ��Ű��(bfs)
		checked = new boolean[N][M];
		int area=1;
		
		for(int i=0; i<N; ++i) {
			for(int j=0; j<M; ++j) {
				if(Map[i][j] != 0 && !checked[i][j]) {
					makeGroupIsland(i, j, area);
					area++;
				}				
			}			
		}
		
		edgeList = new ArrayList<>();
		for(int i=0; i<area; ++i) {
			edgeList.add(new ArrayList<>());
		}
		
		// �׷�ȭ�� ���� ���� ��ǥ�� �������� ��, ��, ��, �� �������� ����Ǵ� �� Ž��(���� ã��)
		for(int i=0; i<N; ++i) {
			for(int j=0; j<M; ++j) {
				if(Map[i][j] >= 1) {
					searchAdjIsland(i, j);
				}				
			}
		}
		
		// �׷����� ������ ������� ���¿��� -> ���� �˰��� ����
		doPrim();		
		
	}

	private static void doPrim() {
		boolean[] marked = new boolean[edgeList.size()];	// ���� 1���� �����ϱ� ������
		PriorityQueue<EdgeInfo_17472> priorityQueue = new PriorityQueue<>();
		
		int startNode = 1;
		marked[startNode] = true;
		
		// ���� ���� ������ ������ ��� ����
		for(EdgeInfo_17472 edge : edgeList.get(startNode)) {
			priorityQueue.offer(edge);
		}
		
		while(!priorityQueue.isEmpty()) {
			EdgeInfo_17472 q = priorityQueue.poll();
			if(marked[q.nodeIdx]) continue;
			
			marked[q.nodeIdx] = true;
			cost += q.cost;
			
			// �ش� ���� ������ ��� ���� ����
			for(EdgeInfo_17472 edge : edgeList.get(q.nodeIdx)) {
				priorityQueue.offer(edge);
			}
		}
		
		// ���� ������ �Ұ����ϴٸ� -> -1
		for(int i=1; i<edgeList.size(); ++i) {
			if(!marked[i]) cost = -1;
		}
	}

	private static void searchAdjIsland(int y, int x) {		
		
		//��, ��, ��, �������� Ž��			
		for(int d=0; d<4; ++d) {
			int cnt=1;	// ���� �� ������ �Ÿ�(���� ���)
			
			while(true) {
				int moveY = y + directions[0][d]*cnt;
				int moveX = x + directions[1][d]*cnt;								
				
				// ������ ����ų�, �ڱ� �ڽ��� ������ ���
				if(!isRanged(moveY, moveX) || Map[moveY][moveX] == Map[y][x]) break;				
				
				// �̵��� �ϴٰ� �ٸ� ���� �����ٸ�
				if(Map[moveY][moveX] != 0 && Map[moveY][moveX] != Map[y][x]) {
					
					// ������ ����� 1���ϸ� ����
					if(cnt-1 > 1) {
						boolean find = false;
						
						// �̹� ���� ������ �����Ѵٸ� ���Ÿ�
						for(EdgeInfo_17472 edge : edgeList.get(Map[y][x])) {
							if(edge.nodeIdx == Map[moveY][moveX]) {
								int origin = edge.cost;
								edge.cost = Math.min(origin, cnt-1);								
								
								find = true;
								break;
							}
						}
						
						if(!find) 
							edgeList.get(Map[y][x]).add(new EdgeInfo_17472(Map[moveY][moveX], cnt-1));																	
					}					
					break;
				}
				
				cnt++;
			}
		}		
	}

	private static void makeGroupIsland(int y, int x, int area) {
		Queue<Position_17472> queue = new LinkedList<>();
		queue.offer(new Position_17472(y, x));
		checked[y][x] = true;
		Map[y][x] = area;
		
		while(!queue.isEmpty()) {
			Position_17472 q = queue.poll();
			
			for(int d=0; d<4; ++d) {
				int moveY = q.y + directions[0][d];
				int moveX = q.x + directions[1][d];
				
				if(isRanged(moveY, moveX) && !checked[moveY][moveX] && Map[moveY][moveX] != 0) {
					queue.offer(new Position_17472(moveY, moveX));
					checked[moveY][moveX] = true;
					Map[moveY][moveX] = area;
				}
			}			
		}		
	}

	private static boolean isRanged(int y, int x) {
		if(y>=0 && y<N && x>=0 && x<M) return true;
		return false;
	}

}
