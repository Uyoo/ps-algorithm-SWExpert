//��ȣ�ʸ�
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Problem_2112 {

	static int D, W; // ��,��
	static int K; // �հ� ����
	static int[][] map; // 0(A), 1(B)
	static int[] checked;
	static int answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int testCase = Integer.parseInt(br.readLine());
		int t = 1;
		while (t <= testCase) {
			String config = br.readLine();
			st = new StringTokenizer(config, " ");
			D = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());

			map = new int[D][W];
			for (int i = 0; i < D; ++i) {
				String input = br.readLine();
				st = new StringTokenizer(input, " ");
				for (int j = 0; j < W; ++j) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			answer = Integer.MAX_VALUE;
			checked = new int[D];
			int idx = 0;
			int medicine=0;
			
			doSolution(idx, medicine);

			System.out.println("#" + t + " " + answer);
			t++;
		}
	}

	private static void doSolution(int idx, int medicine) {
		if(answer < medicine) return;
		if(idx == D) {
			//�ش� ���տ� �°� �����ϰ� üũ�ϱ�
			if(doProcess()) {
				answer = Math.min(answer, medicine);
			}
			return;
		}
		
		checked[idx] = -1;
		doSolution(idx+1, medicine);	// ��ǰ�� �������ϴ� ���	
		
		checked[idx] = 0;
		doSolution(idx+1, medicine+1);	// A ��ǰ�� �����ϴ� ���			
		
		checked[idx] = 1;
		doSolution(idx+1, medicine+1);	// B ��ǰ�� �����ϴ� ���				
	}

	private static boolean doProcess() {
		int[][] tmp = new int[D][W];
		for(int i=0; i<D; ++i) {
			for(int j=0; j<W; ++j) {
				tmp[i][j] = map[i][j];
			}
		}

		//�ش� ���տ� �°� ����
		for(int i=0; i<D; ++i) {
			if(checked[i] == 0) {
				injectMedicine(i, 0, tmp);										
			}
			else if(checked[i] == 1) {
				injectMedicine(i, 1, tmp);				
			}
		}
		
		//�׽�Ʈ
		return checkFilm(tmp);
	}

	private static void injectMedicine(int row, int attribute, int[][] tmp) {			
		for(int j=0; j<W; ++j) {
			tmp[row][j] = attribute;
		}			
	}

	private static boolean checkFilm(int[][] tmp) {
		for(int j=0; j<W; ++j) {			
			int cnt = 1;			
			for(int i=1; i<D; ++i) {
				if (tmp[i-1][j] == tmp[i][j]) cnt++;
				else cnt = 1;			
				
				if(cnt == K) break;
			}
			
			if(cnt < K)	return false;		
		}

		return true;
	}

}
