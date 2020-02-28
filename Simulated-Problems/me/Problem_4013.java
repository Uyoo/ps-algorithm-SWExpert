//Ư���� �ڼ�
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

class MagnetInfo {
	public int idx;
	public int dir;
	
	public MagnetInfo(int idx, int dir) {
		super();
		this.idx = idx;
		this.dir = dir;
	}		
}

public class Problem_4013 {
	
	static int K;	//�ڼ��� ȸ����Ű�� Ƚ��
	static LinkedList<Integer>[] magnets;	
	static int MagnetSize = 4;
	static LinkedList<MagnetInfo> list;
	static boolean[] checked;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int testCase = Integer.parseInt(br.readLine());
		int t=1;
		while(t<=testCase) {
			K = Integer.parseInt(br.readLine());
			magnets = new LinkedList[MagnetSize];			
			
			//�ڼ� ���� �Է¹ޱ�
			for(int i=0; i<MagnetSize; ++i) {
				String input = br.readLine();
				st = new StringTokenizer(input, " ");
				magnets[i] = new LinkedList<>();
				
				while(st.hasMoreTokens()) {
					magnets[i].add(Integer.parseInt(st.nextToken()));
				}
			}
			
			//��� �ϳ� ���� ������ �ڼ� ����			
			for(int i=0; i<K; ++i) {
				String input = br.readLine();
				st = new StringTokenizer(input, " ");
				doSolution(st);				
			}			
			
			//����� �� �����ߴٸ� �� ���
			int answer = 0;
			for(int i=0; i<MagnetSize; ++i) {
				double tmp = 0;
				if(magnets[i].get(0) == 1) {
					tmp = Math.pow(2, i);
				}
				answer += tmp;
			}
			
			System.out.println("#" + t + " " + answer);
			t++;
		}
	}

	private static void doSolution(StringTokenizer st) {
		int magnetIdx = Integer.parseInt(st.nextToken()) - 1;		
		int rotateDir = Integer.parseInt(st.nextToken());	
		list = new LinkedList<>();		
		checked = new boolean[MagnetSize];
			
		list.add(new MagnetInfo(magnetIdx, rotateDir));
		findPossibleMagnet(magnetIdx, rotateDir);
		
		//ȸ�� �����ϴٸ� ȸ��
		for(MagnetInfo magnet : list) {
			doRotate(magnet);
		}		
		
		return;
	}	

	private static void findPossibleMagnet(int magnetIdx, int rotateDir) {		
		//���� Ž��
		int currentIdx = magnetIdx;
		int currentDir = rotateDir;
		while(true) {
			int leftMagnetIdx = currentIdx - 1;
			if(leftMagnetIdx < 0) break;
			
			if(magnets[currentIdx].get(6) != magnets[leftMagnetIdx].get(2)) {
				list.add(new MagnetInfo(leftMagnetIdx, (currentDir*-1)));
			}
			
			else break;
			
			currentIdx = leftMagnetIdx;
			currentDir *= -1;
		}
		
		//������ Ž��
		currentIdx = magnetIdx;
		currentDir = rotateDir;
		while(true) {
			int rightMagnetIdx = currentIdx + 1;
			if(rightMagnetIdx >= MagnetSize) break;
			
			if(magnets[currentIdx].get(2) != magnets[rightMagnetIdx].get(6)) {
				list.add(new MagnetInfo(rightMagnetIdx, (currentDir*-1)));
			}
			
			else break;		
			
			currentIdx = rightMagnetIdx;
			currentDir *= -1;			
		}		
	}

	private static void doRotate(MagnetInfo magnet) {
		//�ð���� ȸ��
		if(magnet.dir == 1) {
			int tmp = magnets[magnet.idx].removeLast();
			magnets[magnet.idx].addFirst(tmp);
		}
		
		//�ݽð���� ȸ��
		else {
			int tmp = magnets[magnet.idx].removeFirst();
			magnets[magnet.idx].addLast(tmp);
		}
	}
}
