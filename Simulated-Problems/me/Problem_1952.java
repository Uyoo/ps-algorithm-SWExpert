// 수영장
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Problem_1952 {
	
	static int M;
	static int[] Fares;	//1일, 1달, 3개월, 1년
	static int[] Month;
	
	static int answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int testCase = Integer.parseInt(br.readLine());
		int t=1;
		while(t<=testCase) {
			M = 12;
			Month = new int[M];
			Fares = new int[4];
			
			// input fare
			String input = br.readLine();
			st = new StringTokenizer(input, " ");
			int size = st.countTokens();
			for(int i=0; i<size; ++i) {
				Fares[i] = Integer.parseInt(st.nextToken());
			}
			
			// input month & 1년 사용했을 때의 금액 구하기
			boolean check = false;			
			input = br.readLine();
			st = new StringTokenizer(input, " ");
			for(int i=0; i<M; ++i) {
				Month[i] = Integer.parseInt(st.nextToken());
				if(Month[i] > 0) check = true;
			}
			
			//수영장 사용일이 모두 0일이라면
			if(!check) {
				answer = 0;
			}
			
			else {
				answer = Integer.MAX_VALUE;
				answer = Math.min(answer, Fares[3]);	//1년 사용량과 먼저 비교
				doSolution();
			}			
			
			System.out.println("#" + t + " " + answer);
			t++;
		}
	}

	private static void doSolution() {
		
		//1일, 1달, 3달
		int index=0;
		int acc=0;
		doProcess(index, acc);
	}

	private static void doProcess(int index, int acc) {
		if(index >= M) {
			answer = Math.min(answer, acc);
			return;
		}		
		
		if(Month[index] <= 0) {
			doProcess(index+1, acc);
		}
		
		else {
			//1일 사용권
			doProcess(index+1, acc+(Month[index] * Fares[0]));
			
			//1달 사용권
			doProcess(index+1, acc+Fares[1]);
			
			//3달 사용권
			doProcess(index+3, acc+Fares[2]);
		}								
	}

}
