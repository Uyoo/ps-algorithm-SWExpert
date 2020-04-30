// 이차원 배열과 연산
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
		
		// 수의 등장 횟수를 기준으로 오름차순
		if(this.count > o.count) {
			return 1;
		}
		
		// 수의 등장 횟수가 같다면
		else if(this.count == o.count) {
			
			// 수 자체로 비교해서 오름차순
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
		
		// input map. 처음에 3*3으로 시작
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
		
		// 찾고자 하는 위치가 범위에 존재한다면
		if(R<=N && C<=M ) {
			
			// 바로 찾았다면 0초
			if(A[R][C] == K) System.out.println(0);
			
			// 아니라면 로직 진행
			else System.out.println(doSolution());
		}
		
		// 찾고자 하는 위치가 범위에 없다면 -> 바로 로직 진행
		else {
			System.out.println(doSolution());
		}		
	}

	private static int doSolution() {
		
		int time = 0;
		while(true) {					
			time++;
			if(time > 100) return -1;
			
			// N * M에서, N >= M인 경우 -> R연산 진행			
			if(N >= M) {
				doOperation(0);				
			}
			
			
			// N * M에서, N < M인 경우 -> C연산 진행
			else if(N < M){
				doOperation(1);
			}
						
			
			// A[r][c] = k라면 멈추기
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
			
			// 모든 행에 대한 정렬 진행			
			for(int i=1; i<=N; ++i) {
				HashMap<Integer, Integer> hashMap = new HashMap<>();				
				
				for(int j=1; j<=M; ++j) {
					
					// 정렬할 때는 0은 무시
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
				
				// 각각의 수에 맞는 카운팅 정보를 리스트에 담기
				LinkedList<ArrayInfo> list = new LinkedList<>();
				for(int key : hashMap.keySet()) {
					list.add(new ArrayInfo(key, hashMap.get(key)));
				}
				
				// 정렬 & 저장
				Collections.sort(list);				
				map_tmp.add(list);				
				max = Math.max(max, list.size()*2);
			}
						
			// 이전의 배열 사이즈보다 작아지면 안됨 or 100을 넘어가면 안됨
			if(max < M) max = M;
			if(max > 100) max = 100;
			
			// 열의 최대 개수로 갱신
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
					
					// 100개 이상인 경우, 초과했을 때 나머지 부분은 버리기
					if(idx > M) break;
				}
				
				// 나머지 부분 0으로 채우기
				while(idx <= M) {
					A[i][idx++] = 0;						
				}					
			}						
			
			break;

		case 1:
			// 모든 열에 대한 정렬 진행
			for(int j=1; j<=M; ++j) {
				HashMap<Integer, Integer> hashMap = new HashMap<>();						
				for(int i=1; i<=N; ++i) {
					
					// 정렬할 때는 0은 무시
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
				
				// 각각의 수에 맞는 카운팅 정보를 리스트에 담기
				LinkedList<ArrayInfo> list = new LinkedList<>();
				for(int key : hashMap.keySet()) {
					list.add(new ArrayInfo(key, hashMap.get(key)));
				}
				
				// 정렬 & 저장
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
					
					// 100개 이상인 경우, 초과했을 때 나머지 부분은 버리기
					if(idx > N) break;
				}
				
				// 나머지 부분 0으로 채우기
				while(idx <= N) {
					A[idx++][i] = 0;						
				}								
			}
			
			break;
		}		
	}

}
