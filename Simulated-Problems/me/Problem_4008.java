package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Problem_4008 {
	
	static int N;	//피연산자 카드 개수
	static int[] operation;	//연산자 (+,-,*,/)
	static int[] cards;		//피연산자
	static int max;
	static int min;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int testCase = Integer.parseInt(br.readLine());
		int t=1;
		while(t <= testCase) {
			N = Integer.parseInt(br.readLine());
			operation = new int[4];
			cards = new int[N];
			max = Integer.MIN_VALUE;
			min = Integer.MAX_VALUE;
			
			String input_oper = br.readLine();
			st = new StringTokenizer(input_oper, " ");
			int idx=0;
			while(st.hasMoreTokens()) {
				operation[idx++] = Integer.parseInt(st.nextToken());
			}
			
			String input_cards = br.readLine();
			st = new StringTokenizer(input_cards, " ");
			for(int i=0; i<N; ++i) {
				cards[i] = Integer.parseInt(st.nextToken());
			}
			
			int index=1;
			int acc=cards[0];
			doSolution(index, acc);
			
			System.out.println("#" + t + " " + (max - min));
			t++;
		}
		
	}

	private static void doSolution(int index, int acc) {			
		if(index == N) {
			max = Math.max(max, acc);
			min = Math.min(min, acc);
			return;
		}
		
		for(int i=0; i<4; ++i) {
			if(operation[i] > 0) {
				operation[i]--;
				if(i == 0) {
					doSolution(index+1, acc+cards[index]);	
				} else if(i == 1) {
					doSolution(index+1, acc-cards[index]);
				} else if(i == 2) {
					doSolution(index+1, acc*cards[index]);
				} else if(i == 3) {
					doSolution(index+1, acc/cards[index]);
				}							
				operation[i]++;
			}
		}
	}

}
