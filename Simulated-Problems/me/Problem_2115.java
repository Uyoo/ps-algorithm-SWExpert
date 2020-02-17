//����ä��
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Problem_2115 {

	static int N; // ������� ũ��
	static int M; // ���ð����� ���� ����
	static int C; // ä�� ������ �ִ��
	static int[][] map;
	static boolean[][] checked;
	static int result;	

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
			C = Integer.parseInt(st.nextToken());

			map = new int[N][N];
			checked = new boolean[N][N];

			for (int i = 0; i < N; ++i) {
				input = br.readLine();
				st = new StringTokenizer(input, " ");
				for (int j = 0; j < N; ++j) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
				
			//A�� ������ �̵�
			int answer = 0;
			for(int i=0; i<N; ++i) {
				for(int j=0; j<N-M+1; ++j) {
					answer = Math.max(answer, doProcess(i, j));
				}
			}

			System.out.println("#" + t + " " + answer);
			t++;
		}
	}


	private static int doProcess(int y, int x) {
		for(int m=0; m<M; ++m) {
			checked[y][x+m] = true;
		}
		
		//A���� ���� �� ���
		result = 0;
		int cnt = 0, sum = 0, price = 0;
		int valueA = 0, valueB = 0;
		
		getPrice(y, x, cnt, sum, price);
		valueA = result;
		
		//B�� ä�� ������ ���� Ž�� -> �� ���		
		for(int i=0; i<N; ++i) {
			for(int j=0; j<N-M+1; ++j) {
				if(isPossible(i, j)) {
					result = 0;
					getPrice(i, j, cnt, sum, price);
					valueB = Math.max(valueB, result);
				}
			}
		}
		
		for(int m=0; m<M; ++m) {
			checked[y][x+m] = false;
		}
		
		return valueA + valueB;
	}


	private static void getPrice(int y, int x, int cnt, int sum, int price) {
		if(sum > C) return;
		result = Math.max(result, price);
		if(cnt == M) return;	

		//ä���� ���ܵ� �߿��� C ���� ���� ������ ��� ������ ���
		getPrice(y, x+1, cnt+1, sum + map[y][x], price + (map[y][x] * map[y][x]));		
		getPrice(y, x+1, cnt+1, sum, price);
	}


	private static boolean isPossible(int y, int x) {
		boolean possible = true;
		for(int m=0; m<M; ++m) {
			if(checked[y][x+m]) {
				possible = false;
				break;
			}
		}
		
		return possible;
	}
}
