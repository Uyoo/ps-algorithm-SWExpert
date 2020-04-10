//점심 식사 시간
package me;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class PersonInfo implements Comparable<PersonInfo>{
	public int y, x;
	public int time;
	public int limit;
	public boolean wait;	//대기 표시

	public PersonInfo(int y, int x, int time, int limit) {
		super();
		this.y = y;
		this.x = x;
		this.time = time;
		this.limit = limit;
	}

	@Override
	public int compareTo(PersonInfo o) {
		return this.limit - o.limit;
	}
}

public class Problem_2383 {

	static int N;
	static int[][] Map;
	static ArrayList<PersonInfo> Person_list;
	static ArrayList<PersonInfo> Step_List;

	static boolean[] checked;
	static int Full = 3;
	
	static int cnt;
	static int answer;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int testCase = Integer.parseInt(br.readLine());
		int t = 1;
		while (t <= testCase) {
			String input = br.readLine();
			N = Integer.parseInt(input);
			Map = new int[N][N];

			// input map, 사람 정보 리스트에 담기
			Person_list = new ArrayList<>();
			Step_List = new ArrayList<>();
			for (int i = 0; i < N; ++i) {
				input = br.readLine();
				st = new StringTokenizer(input, " ");
				for (int j = 0; j < N; ++j) {
					Map[i][j] = Integer.parseInt(st.nextToken());

					if (Map[i][j] == 1) {
						Person_list.add(new PersonInfo(i, j, 0, 0));
					}
					else if(Map[i][j] > 1) {
						Step_List.add(new PersonInfo(i, j, Map[i][j], 0));
					}
				}
			}

			// 가까운 계단의 큐로 사람의 정보 삽입
			answer = Integer.MAX_VALUE;
			doSolution();

			System.out.println("#" + t + " " + answer);
			t++;
		}
	}

	private static void doSolution() {
		// 1번, 2번계단에 진입하는 사람의 경우의 수 고려(조합)
		int index = 0;
		checked = new boolean[Person_list.size()];
		int limit = Person_list.size();
		doProcess(index, limit);

	}

	private static void doProcess(int index, int limit) {
		if (index >= limit) {
			// 계단 1과 계단 2에 대한 사람 구분
			doStart();
			return;
		}

		checked[index] = true;
		doProcess(index+1, limit);
		
		checked[index] = false;
		doProcess(index+1, limit);
	}

	private static void doStart() {
		ArrayList<PersonInfo> person_step1 = new ArrayList<>();
		ArrayList<PersonInfo> person_step2 = new ArrayList<>();
		for (int i = 0; i < Person_list.size(); ++i) {
			if (checked[i]) {
				person_step1.add(Person_list.get(i));
			} else {
				person_step2.add(Person_list.get(i));
			}
		}

		//각 계단별로 사람 위치와의 거리 구해놓기
		PersonInfo step1 = Step_List.get(0);
		for(PersonInfo person : person_step1) {
			int distance = Math.abs(person.y - step1.y) + Math.abs(person.x - step1.x);
			person.time = 0;
			person.limit = distance;
		}
		
		PersonInfo step2 = Step_List.get(1);
		for(PersonInfo person : person_step2) {
			int distance = Math.abs(person.y - step2.y) + Math.abs(person.x - step2.x);
			person.time = 0;
			person.limit = distance;
		}
		
		//0분부터 1분씩 증가시켜 시뮬레이션
		int value = doSimulation(person_step1, person_step2);
		answer = Math.min(answer, value);
	}

	private static int doSimulation(ArrayList<PersonInfo> person_step1, ArrayList<PersonInfo> person_step2) {
		Queue<PersonInfo> queue_step1 = new LinkedList<>();
		Queue<PersonInfo> queue_step2 = new LinkedList<>();
				
		cnt=0;
		int size = person_step1.size() + person_step2.size();
		int time = 0;
		while(true) {			
//			if(person_step1.size() == 0 && person_step2.size()==0 
//					&& queue_step1.isEmpty() && queue_step2.isEmpty()) break;			
			
			if(cnt == size) break;
			time++;
			
			//계단1
			int type=0;
			doCalculate(person_step1, queue_step1, type);
			
			//계단2
			type=1;
			doCalculate(person_step2, queue_step2, type);						
		}	
		
		return time;
	}

	private static void doCalculate(ArrayList<PersonInfo> person_step, Queue<PersonInfo> queue_step, int type) {
		Collections.sort(person_step);
		
		//대기열에 사람이 존재한다면 -> 계단을 다 내려갔다면 -> pop
		if(queue_step.size() > 0) {			
			
			int size = queue_step.size();
			for(int s=0; s<size; ++s) {
				PersonInfo q = queue_step.poll();
				
				q.time++;
				if(q.time < q.limit) {
					queue_step.offer(q);
				}
				
				else cnt++;
			}
		}
		
		ArrayList<PersonInfo> removeIdxList = new ArrayList<>();
		for(int i=0; i<person_step.size(); ++i) {
			PersonInfo person = person_step.get(i);
			person.time++;
			
			//계단에 도달했다면 			
			if(person.time == person.limit) {
				
				//대기큐의 사이즈가 3개로 가득 찼는지 여부 판단
				//가능하다면 -> 계단을 내려가기까지의 시간 갱신
				if(queue_step.size() < Full) {
					
					//해당 사람이 이전에 대기하고 있었다면 -> 바로 계단 내려가도록 하기
					if(person.wait) {
						person.limit = (Step_List.get(type).time+1);
						person.time = 1;
					}
					
					else {
						person.limit = (Step_List.get(type).time+1);
						person.time = 0;
					}
					
					person.wait = false;
					queue_step.offer(person);					
					removeIdxList.add(person);
				}
				
				//대기해야한다면 1분 감소시키고 대기표시
				else {
					person.wait = true;
					person.time--;
				}
			}
		}
		
		//계단에 들어간 사람들은 제거
		for(PersonInfo removePerson : removeIdxList)
			person_step.remove(removePerson);
				
	}

}
