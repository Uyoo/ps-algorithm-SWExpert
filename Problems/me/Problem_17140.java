// ������ �迭�� ����
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.StringTokenizer;

class ArrayInfo implements Comparable<ArrayInfo>{
	public int num;
	public int count;
	
	public ArrayInfo(int num, int count) {
		super();
		this.num = num;
		this.count = count;
	}

	@Override
	public int compareTo(ArrayInfo o) {
		
		// ���� ���� Ƚ���� �������� ��������
		if(this.count > o.count) {
			return 1;
		}
		
		// ���� ���� Ƚ���� ���ٸ�
		else if(this.count == o.count) {
			
			// �� ��ü�� ���ؼ� ��������
			return this.num - o.num;
		}
		
		return -1;
	}	
}

public class Problem_17140 {
	
	static int R,C;
	static int K;
	static int[][] A;
	static int N, M;
	static int answer;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;		
		
		String input = br.readLine();
		st = new StringTokenizer(input, " ");
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		// input map. ó���� 3*3���� ����
		N = 3;
		M = 3;
		A = new int[N+1][M+1];
		for(int i=1; i<=N; ++i) {
			input = br.readLine();
			st = new StringTokenizer(input, " ");
			for(int j=1; j<=M; ++j) {
				A[i][j] = Integer.parseInt(st.nextToken());				
			}
		}
		
		// ã���� �ϴ� ��ġ�� ������ �����Ѵٸ�
		if(R<=N && C<=M ) {
			
			// �ٷ� ã�Ҵٸ� 0��
			if(A[R][C] == K) System.out.println(0);
			
			// �ƴ϶�� ���� ����
			else System.out.println(doSolution());
		}
		
		// ã���� �ϴ� ��ġ�� ������ ���ٸ� -> �ٷ� ���� ����
		else {
			System.out.println(doSolution());
		}		
	}

	private static int doSolution() {
		
		int time = 0;
		while(true) {					
			time++;
			if(time > 100) return -1;
			
			// N * M����, N >= M�� ��� -> R���� ����			
			if(N >= M) {
				doOperation(0);				
			}
			
			
			// N * M����, N < M�� ��� -> C���� ����
			else if(N < M){
				doOperation(1);
			}
						
			
			// A[r][c] = k��� ���߱�
			if(R<=N && C<=M) {
				if(A[R][C] == K) break;
			}		
			
		}
		
		return time;
	}

	private static void doOperation(int type) {
		
		ArrayList<LinkedList<ArrayInfo>> map_tmp = new ArrayList<>();
		int max = 0;
		
		switch (type) {
		case 0:
			
			// ��� �࿡ ���� ���� ����			
			for(int i=1; i<=N; ++i) {
				HashMap<Integer, Integer> hashMap = new HashMap<>();				
				
				for(int j=1; j<=M; ++j) {
					
					// ������ ���� 0�� ����
					if(A[i][j] > 0) {
						if(!hashMap.containsKey(A[i][j])) {
							hashMap.put(A[i][j], 1);
						}
						else {
							int count = hashMap.get(A[i][j]);
							hashMap.put(A[i][j], count+1);
						}
					}		
				}	
				
				// ������ ���� �´� ī���� ������ ����Ʈ�� ���
				LinkedList<ArrayInfo> list = new LinkedList<>();
				for(int key : hashMap.keySet()) {
					list.add(new ArrayInfo(key, hashMap.get(key)));
				}
				
				// ���� & ����
				Collections.sort(list);				
				map_tmp.add(list);				
				max = Math.max(max, list.size()*2);
			}
						
			// ������ �迭 ������� �۾����� �ȵ� or 100�� �Ѿ�� �ȵ�
			if(max < M) max = M;
			if(max > 100) max = 100;
			
			// ���� �ִ� ������ ����
			M = max;
			A = new int[N+1][M+1];
			for(int i=1; i<=map_tmp.size(); ++i) {
				LinkedList<ArrayInfo> list = map_tmp.get(i-1);							
				
				int idx=1;
				for(int a=0; a<list.size(); ++a) {
					int num = list.get(a).num;
					int count = list.get(a).count;
					
					A[i][idx++] = num;
					A[i][idx++] = count;
					
					// 100�� �̻��� ���, �ʰ����� �� ������ �κ��� ������
					if(idx > M) break;
				}
				
				// ������ �κ� 0���� ä���
				while(idx <= M) {
					A[i][idx++] = 0;						
				}					
			}						
			
			break;

		case 1:
			// ��� ���� ���� ���� ����
			for(int j=1; j<=M; ++j) {
				HashMap<Integer, Integer> hashMap = new HashMap<>();						
				for(int i=1; i<=N; ++i) {
					
					// ������ ���� 0�� ����
					if(A[i][j] > 0) {
						if(!hashMap.containsKey(A[i][j])) {
							hashMap.put(A[i][j], 1);
						}
						else {
							int count = hashMap.get(A[i][j]);
							hashMap.put(A[i][j], count+1);
						}
					}					
				}	
				
				// ������ ���� �´� ī���� ������ ����Ʈ�� ���
				LinkedList<ArrayInfo> list = new LinkedList<>();
				for(int key : hashMap.keySet()) {
					list.add(new ArrayInfo(key, hashMap.get(key)));
				}
				
				// ���� & ����
				Collections.sort(list);				
				map_tmp.add(list);
				max = Math.max(max, list.size()*2);
			}
			
			
			if(max < N) max = N;
			if(max > 100) max = 100;
			
			N = max;
			A = new int[N+1][M+1];						
			for(int i=1; i<=map_tmp.size(); ++i) {
				LinkedList<ArrayInfo> list = map_tmp.get(i-1);
				
				int idx=1;
				for(int a=0; a<list.size(); ++a) {
					int num = list.get(a).num;
					int count = list.get(a).count;
					
					A[idx++][i] = num;
					A[idx++][i] = count;
					
					// 100�� �̻��� ���, �ʰ����� �� ������ �κ��� ������
					if(idx > N) break;
				}
				
				// ������ �κ� 0���� ä���
				while(idx <= N) {
					A[idx++][i] = 0;						
				}								
			}
			
			break;
		}		
	}

}
