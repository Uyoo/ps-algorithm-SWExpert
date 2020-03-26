//요리사
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Problem_4012 {
	static int N;
	static int[][] S;
	static int[][] Manues;

	static int answer;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int testCase = Integer.parseInt(br.readLine());
		int t=1;
		while(t<=testCase) {
			String input = br.readLine();
			N = Integer.parseInt(input);
			S = new int[N+1][N+1];
			Manues = new int[2][N+1];
			
			//input map
			for(int i=1; i<=N; ++i) {
				input = br.readLine();
				st = new StringTokenizer(input, " ");
				for(int j=1; j<=N; ++j) {
					S[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			answer = Integer.MAX_VALUE;
			doSolution();
			
			System.out.println("#" + t + " " + answer);
			t++;
		}
		
	}

	private static void doSolution() {
		int mid = N/2;
		
		//A,B 음식을 만들기 위한 경우의 수 탐색
		int index=1;
		int count_A = 1;
		int count_B = 1;
		doProcess(index, count_A, count_B, mid);
		
	}

	private static void doProcess(int index, int count_A, int count_B, int mid) {
		if(count_A > mid && count_B > mid) {
			//A,B의 맛을 구하기
			getFlavor(mid);
			
			return;
		}
		
		for(int i=index; i<=N; ++i) {			
			
			//A 음식에 대한 경우
			if(count_A <= mid) {
				Manues[0][count_A] = i;
				doProcess(i+1, count_A+1, count_B, mid);
			}
			
			//B 음식에 대한 경우			
			if(count_B <= mid) {
				Manues[1][count_B] = i;
				doProcess(i+1, count_A, count_B+1, mid);
			}
		}
	}

	private static void getFlavor(int mid) {
		ArrayList<Integer> list_A = new ArrayList<>();
		ArrayList<Integer> list_B = new ArrayList<>();		

		for(int i=1; i<=mid; ++i) {
			list_A.add(Manues[0][i]);
		}
		
		for(int i=1; i<=mid; ++i) {
			list_B.add(Manues[1][i]);
		}	
		
		//A의 맛에 대한 점수 구하기(조합)
		int scoreA = getScore(list_A);	
		
		//B의 맛에 대한 점수 구하기
		int scoreB = getScore(list_B);
		
		answer = Math.min(answer, Math.abs(scoreA - scoreB));		
	}

	private static int getScore(ArrayList<Integer> list) {
		int score = 0;
		
		for(int i=0; i<list.size()-1; ++i) {
			int base = list.get(i);			
			
			for(int j=i+1; j<list.size(); ++j) {
				int next = list.get(j);
				score += S[base][next];
				score += S[next][base];
			}
		}
		
		return score;
	}

}
