//줄기세포 배양
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;


class CellInfo implements Comparable<CellInfo> {
	public int y,x;
	public int life;		//생명력
	public int time;		//시간
	
	public CellInfo(int y, int x, int life, int time) {
		super();
		this.y = y;
		this.x = x;
		this.life = life;
		this.time = time;		
	}
	
	@Override
	public int compareTo(CellInfo o) {		
		return o.life- this.life;
	}		
}

public class Problem_5653 {
	
	static int N;	//세로크기(열)
	static int M;	//가로크기(행)
	static int K;	//배양시간
	static int[][] cell;
	static int Size;
	static boolean[][] checked;
	
	static Queue<CellInfo> queue;
	static int[][] directions = {
			{-1, 0, 1, 0},
			{0, 1, 0, -1}
	};	

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int testCase = Integer.parseInt(br.readLine());
		int t=1;
		while(t<=testCase) {
			String input = br.readLine();
			st = new StringTokenizer(input, " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			Size = 1000;
			cell = new int[Size][Size];
			queue = new LinkedList<>();
			checked = new boolean[Size][Size];
			
			//초기 값을 중앙으로 이동
			for(int i=0; i<N; ++i) {
				input = br.readLine();
				st = new StringTokenizer(input, " ");
				
				for(int j=0; j<M; ++j) {
					int y = i+(Size/2);
					int x = j+(Size/2);
					
					cell[y][x] = Integer.parseInt(st.nextToken());
					if(cell[y][x] > 0) {
						queue.offer(new CellInfo(y, x, cell[y][x], cell[y][x]));
						checked[y][x] = true;
					}
				}
			}
			
			int answer = doSolution();
			
			System.out.println("#" + t + " " + answer);
			t++;
		}

	}

	private static int doSolution() {
		int count = 0;
		
		//초기상태에 있는 큐를 꺼내고 -> 활성화 가능한지 여부 및 변경 -> 활성화 및 비활성화는 큐에 담기
		count = doProcess();
		
		//남은 큐의 개수 리턴
		return count;		
	}

	private static int doProcess() {		
		int t=1;
		while(!queue.isEmpty()) {
			int size = queue.size();
			//System.out.println("size: " + size);
			Collections.sort((List<CellInfo>) queue);
			
			for(int s=0; s<size; ++s) {
				CellInfo q = queue.poll();			
								
				//현재 세포가 비활성화 상태라면 -> time을 하나씩 차감시키면서 활성화로 전환
				if(q.time > 0) {
					q.time--;
					queue.offer(q);					
				}				
				
				//현재 활성화 상태라면 -> 인접한 곳에 번식
				else if(q.time <= 0) {										
					for(int d=0; d<4; ++d) {
						int moveY = q.y + directions[0][d];
						int moveX = q.x + directions[1][d];
						
						if(isRanged(moveY, moveX) && !checked[moveY][moveX]) {
							queue.offer(new CellInfo(moveY, moveX, q.life, q.life));
							checked[moveY][moveX] = true;				
						}						
					}
					
					//번식하는 순간에도 1시간씩 카운팅
					q.time--;
					if(Math.abs(q.time) < q.life) {
						queue.offer(q);
					}					
				}									
			}
			
			if(t == K) break;			
			t++;
		}
		
		return queue.size();
	}

	private static boolean isRanged(int y, int x) {
		if(y>=0 && y<Size && x>=0 && x<Size) return true;
		return false;
	}

}
