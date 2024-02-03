package Main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Object_Example{
	

	// 메인 함수
	public static void main(String[] args)throws Exception{
				
		//두개의 값을 기준으로 그룹핑=============================================================================================================================
		List<Dummy> list2 = new ArrayList<>();
		
		for(int i=1; i<=3; i++) {
			for(int j=1; j<=3; j++) {
				Dummy dummy = new Dummy(i,i,j);
				list2.add(dummy);
			}
		}
		list2.forEach((key)->{
			/*
			 * [출력결과]
			 * [ masterKey : 1, subKey : 1, data : 1 ]
			 * [ masterKey : 1, subKey : 1, data : 2 ]
			 * [ masterKey : 1, subKey : 1, data : 3 ]
			 * [ masterKey : 2, subKey : 2, data : 1 ]
			 * [ masterKey : 2, subKey : 2, data : 2 ]
			 * [ masterKey : 2, subKey : 2, data : 3 ]
			 * [ masterKey : 3, subKey : 3, data : 1 ]
			 * [ masterKey : 3, subKey : 3, data : 2 ]
			 * [ masterKey : 3, subKey : 3, data : 3 ]
			 * */
			System.out.println(key);
		});

		Map<Object, List<Dummy>> group2 
		= list2.stream().collect(Collectors.groupingBy(dummy -> dummy.getMasterKey()+"-"+dummy.getSubKey()));
		
		group2.forEach((key, value)->{
			/*
			 * [출력결과]
			 * key : 1-1
			 * value : [[ masterKey : 1, subKey : 1, data : 1 ], [ masterKey : 1, subKey : 1, data : 2 ], [ masterKey : 1, subKey : 1, data : 3 ]]
			 * key : 2-2
			 * value : [[ masterKey : 2, subKey : 2, data : 1 ], [ masterKey : 2, subKey : 2, data : 2 ], [ masterKey : 2, subKey : 2, data : 3 ]]
			 * key : 3-3
			 * value : [[ masterKey : 3, subKey : 3, data : 1 ], [ masterKey : 3, subKey : 3, data : 2 ], [ masterKey : 3, subKey : 3, data : 3 ]]
			 * */
			System.out.println("key : "+key);
			System.out.println("value : "+value);
		});

		
		//중첩 그룹핑=============================================================================================================================
		// groupingBy를 중첩해 사용하면 각 레벨로 그룹화된 맵을 생성할 수 있다. 
		// groupingBy는 하나의 그룹 레벨을 나타내고, 중첩되는 순서에따라 그룹화 된다.

		List<Dummy> list3 = new ArrayList<>();
		int masterKey = 1;
		int subKey = 1;
		for(int i=1; i<=6; i++) {
			Dummy dummy = new Dummy(masterKey,subKey,i);
			list3.add(dummy);
			if(i == 3)
				masterKey+=1;
			subKey = i % 3==0 ? 1 : 2;
		}
		list3.forEach(dummy->{
			/*
			 * [실행 결과]
			 * [masterKey,subKey,value]  : 1 , 1 , 1
			 * [[masterKey,subKey,value] : 1 , 2 , 2
			 * [[masterKey,subKey,value] : 1 , 2 , 3
			 * [[masterKey,subKey,value] : 2 , 1 , 4
			 * [[masterKey,subKey,value] : 2 , 2 , 5
			 * [[masterKey,subKey,value] : 2 , 2 , 6
			 * */
			System.out.println(dummy);
		});

		Map<Object, Map<Object, List<Dummy>>> group3 =
		 list3.stream()
			.collect(Collectors.groupingBy(	
											Dummy::getMasterKey, 
					 						Collectors.groupingBy(Dummy::getSubKey) 
					 						)
					);
		
		group3.forEach((key,value)->{
			/*
			 * [실행 결과]
			 * masterKey : 1
			 * subKey : 1
			 * value1 : [[ masterKey : 1, subKey : 1, data : 1 ]]
			 * subKey : 2
			 * value1 : [[ masterKey : 1, subKey : 2, data : 2 ], [ masterKey : 1, subKey : 2, data : 3 ]]
			 * ====================
			 * masterKey : 2
			 * subKey : 1
			 * value1 : [[ masterKey : 2, subKey : 1, data : 4 ]]
			 * subKey : 2
			 * value1 : [[ masterKey : 2, subKey : 2, data : 5 ], [ masterKey : 2, subKey : 2, data : 6 ]]
			 * */
			System.out.println("masterKey : "+key);
			value.forEach((key1,value1)->{
				System.out.println("subKey : "+key1);
				System.out.println("value1 : "+value1);
			});
			System.out.println("====================");
		});

			// 그룹핑을 이용한 통계 구해오기 =============================================================================================================================
		List<Dummy> list4 = new ArrayList<>();
		int key = 1;
		for(int i=1; i<=6; i++) {
			Dummy dummy = new Dummy(key,0,i);
			list4.add(dummy);
			if(i % 2 == 0) key++;
		}
		list4.forEach((dummy)->{
			/*
			 * [출력결과]
			 * [ masterKey : 1, subKey : 0, data : 1 ]
			 * [ masterKey : 1, subKey : 0, data : 2 ]
			 * [ masterKey : 2, subKey : 0, data : 3 ]
			 * [ masterKey : 2, subKey : 0, data : 4 ]
			 * [ masterKey : 3, subKey : 0, data : 5 ]
			 * [ masterKey : 3, subKey : 0, data : 6 ]
			 * */
			System.out.println(dummy);
		});

		// 합계 구해오기
		Map<Integer, Integer> group4 = list4.stream()
				.collect( Collectors.groupingBy(
													Dummy::getMasterKey, 
													Collectors.summingInt(Dummy::getData)
											    )
						);
		group4.forEach((key1,value1)->{
			/*
			 * [출력결과]
			 * [ key, value ] : 1 / 3
			 * [ key, value ] : 2 / 7
			 * [ key, value ] : 3 / 11
			 * */
			System.out.println("[ key, value ] : "+key1+" / "+value1);
		});
		
		// 평균 구하는 방법
		Map<Integer, Double> group5 = list4.stream()
				.collect( Collectors.groupingBy(
													Dummy::getMasterKey,
													Collectors.averagingInt(Dummy::getData)
												)
						);
		group5.forEach((key1,value1)->{
			/*
			 * [출력결과]
			 * [ key, value ] : 1 / 1.5
			 * [ key, value ] : 2 / 3.5
			 * [ key, value ] : 3 / 5.5
			 * */
			System.out.println("[ key, value ] : "+key1+" / "+value1);
		});
		
		// 그룹당 최대 최소 구해오기
		Map<Integer, Dummy> group6 = list4.stream()
				.collect( Collectors.groupingBy(
													Dummy::getMasterKey,
							                        Collectors.collectingAndThen(
							                        		Collectors.minBy(Comparator.comparingInt(Dummy::getData)),
							                        		//Collectors.maxBy(Comparator.comparingInt(Dummy::getData)),
							                        		optional -> optional.map(dummy -> new Dummy(dummy.getMasterKey(),dummy.getSubKey(), dummy.getData())).orElse(null)
							                        )
												)
						);
		group6.forEach((key1,value1)->{
			/*
			 * [minBy출력결과]
			 * [ key, value ] : 1 / [ masterKey : 1, subKey : 0, data : 1 ]
			 * [ key, value ] : 2 / [ masterKey : 2, subKey : 0, data : 3 ]
			 * [ key, value ] : 3 / [ masterKey : 3, subKey : 0, data : 5 ]
			 * [maxBy출력결과]
			 * [ key, value ] : 1 / [ masterKey : 1, subKey : 0, data : 2 ]
			 * [ key, value ] : 2 / [ masterKey : 2, subKey : 0, data : 4 ]
			 * [ key, value ] : 3 / [ masterKey : 3, subKey : 0, data : 6 ]
			 * */
			System.out.println("[ key, value ] : "+key1+" / "+value1);
		});
	
		// 그룹별 통계 정보 구해오기
		Map<Integer, IntSummaryStatistics> group7 = list4.stream()
				.collect( Collectors.groupingBy(
													Dummy::getMasterKey,
													Collectors.summarizingInt(Dummy::getData)
												)
						
						);
		group7.forEach((key1,value1)->{
			/*
			 * [summarizingInt 실행 결과]
			 * [ key, value ] : 1 / IntSummaryStatistics{count=2, sum=3, min=1, average=1.500000, max=2}
			 * [ key, value ] : 2 / IntSummaryStatistics{count=2, sum=7, min=3, average=3.500000, max=4}
			 * [ key, value ] : 3 / IntSummaryStatistics{count=2, sum=11, min=5, average=5.500000, max=6}
			 * */
			System.out.println("[ key, value ] : "+key1+" / "+value1);
		});
	}
	
	static class Dummy{
		int masterKey;
		int subKey;
		int data;
		Dummy(int masterKey, int subKey, int data){
			this.masterKey = masterKey;
			this.subKey = subKey;
			this.data = data;
		}
		public int getMasterKey() {
			return masterKey;
		}
		public int getSubKey() {
			return subKey;
		}
		public int getData() {
			return data;
		}

		public String toString() {
			return "[ masterKey : "+masterKey+", "+"subKey : "+subKey+", "+"data : "+data+" ]";
		}
		
	}
	
}