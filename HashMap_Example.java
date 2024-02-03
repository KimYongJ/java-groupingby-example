package Main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class HashMap_Example{
	

	// 메인 함수
	public static void main(String[] args)throws Exception{
		//하나의 값을 기준으로 그룹핑=============================================================================================================================
		List<HashMap<String,Object>> list1 = new ArrayList<>();

		for(int i=1; i<=3; i++) {
			for(int j=1; j<=3; j++) {
				HashMap<String,Object> hm = new HashMap<String,Object>();
				hm.put("key", i);
				hm.put("value",j);
				list1.add(hm);
			}
		}
		list1.forEach((key)->{
			/*
			 * [실행결과]
			 * 값 : {value=1, key=1}
			 * 값 : {value=2, key=1}
			 * 값 : {value=3, key=1}
			 * 값 : {value=1, key=2}
			 * 값 : {value=2, key=2}
			 * 값 : {value=3, key=2}
			 * 값 : {value=1, key=3}
			 * 값 : {value=2, key=3}
			 * 값 : {value=3, key=3}
			 * */
			System.out.println("값 : "+key);
		});

		Map<Object, List<HashMap<String,Object>>> group1
		= list1.stream()
         .collect(Collectors.groupingBy(hm -> hm.get("key")));
		group1.forEach((key,value)->{
			/*
			 * [실행 결과]
			 * key : 1
			 * value : [{value=1, key=1}, {value=2, key=1}, {value=3, key=1}]
			 * key : 2
			 * value : [{value=1, key=2}, {value=2, key=2}, {value=3, key=2}]
		  	 * key : 3
			 * value : [{value=1, key=3}, {value=2, key=3}, {value=3, key=3}]
			 * */
			System.out.println("key : "+key);
			System.out.println("value : "+value);
		});

		
		//두개의 값을 기준으로 그룹핑=============================================================================================================================
		List<HashMap<String,Object>> list2 = new ArrayList<>();
		
		for(int i=1; i<=3; i++) {
			for(int j=1; j<=3; j++) {
				HashMap<String, Object> hm = new HashMap<>();
				hm.put("key1", i);
				hm.put("key2", i);
				hm.put("value", j);
				list2.add(hm);
			}
		}
		list2.forEach((key)->{
			/*
			 * [실행결과]
			 * 값 : {key1=1, key2=1, value=1}
			 * 값 : {key1=1, key2=1, value=2}
			 * 값 : {key1=1, key2=1, value=3}
			 * 값 : {key1=2, key2=2, value=1}
			 * 값 : {key1=2, key2=2, value=2}
			 * 값 : {key1=2, key2=2, value=3}
			 * 값 : {key1=3, key2=3, value=1}
			 * 값 : {key1=3, key2=3, value=2}
			 * 값 : {key1=3, key2=3, value=3}
			 * */
			System.out.println("값 : "+key);
		});

		Map<Object, List<HashMap<String,Object>>> group2 
		= list2.stream().collect(Collectors.groupingBy(hm -> hm.get("key1")+"-"+hm.get("key2")));
		
		group2.forEach((key, value)->{
			/*
			 * 
			 * [실행 결과]
			 * key : 1-1
			 * value : [{key1=1, key2=1, value=1}, {key1=1, key2=1, value=2}, {key1=1, key2=1, value=3}]
			 * key : 2-2
			 * value : [{key1=2, key2=2, value=1}, {key1=2, key2=2, value=2}, {key1=2, key2=2, value=3}]
			 * key : 3-3
			 * value : [{key1=3, key2=3, value=1}, {key1=3, key2=3, value=2}, {key1=3, key2=3, value=3}]
			 * */
			System.out.println("key : "+key);
			System.out.println("value : "+value);
		});

		
		//중첩 그룹핑=============================================================================================================================
		// groupingBy를 중첩해 사용하면 각 레벨로 그룹화된 맵을 생성할 수 있다. 
		// groupingBy는 하나의 그룹 레벨을 나타내고, 중첩되는 순서에따라 그룹화 된다.

		List<HashMap<String, Integer>> list3 = new ArrayList<>();
		int masterKey = 1;
		int subKey = 1;
		for(int i=1; i<=6; i++) {
			HashMap<String, Integer> hm = new HashMap<>();
			hm.put("masterKey", masterKey);
			hm.put("subKey", 	subKey);
			hm.put("value", 	i);
			list3.add(hm);
			if(i == 3)
				masterKey+=1;
			subKey = i % 3==0 ? 1 : 2;
		}
		list3.forEach(hm->{
			/*
			 * [실행 결과]
			 * [masterKey,subKey,value]  : 1 , 1 , 1
			 * [[masterKey,subKey,value] : 1 , 2 , 2
			 * [[masterKey,subKey,value] : 1 , 2 , 3
			 * [[masterKey,subKey,value] : 2 , 1 , 4
			 * [[masterKey,subKey,value] : 2 , 2 , 5
			 * [[masterKey,subKey,value] : 2 , 2 , 6
			 * */
			System.out.println("[masterKey,subKey,value] : "+hm.get("masterKey")+" , "+hm.get("subKey")+" , "+hm.get("value"));
		});

		Map<Object, Map<Object, List<HashMap<String, Integer>>>> group3 =
		 list3.stream()
			.collect(Collectors.groupingBy(hm->hm.get("masterKey"), 
					 Collectors.groupingBy(hm->hm.get("subKey")) ));
		
		group3.forEach((key,value)->{
			/*
			 * [실행 결과]
			 * masterKey : 1
			 * subKey : 1
			 * value1 : [{subKey=1, masterKey=1, value=1}]
			 * subKey : 2
			 * value1 : [{subKey=2, masterKey=1, value=2}, {subKey=2, masterKey=1, value=3}]
			 * ====================
			 * masterKey : 2
			 * subKey : 1
			 * value1 : [{subKey=1, masterKey=2, value=4}]
			 * subKey : 2
			 * value1 : [{subKey=2, masterKey=2, value=5}, {subKey=2, masterKey=2, value=6}]
			 * */
			System.out.println("masterKey : "+key);
			value.forEach((key1,value1)->{
				System.out.println("subKey : "+key1);
				System.out.println("value1 : "+value1);
			});
			System.out.println("====================");
		});

		// 그룹핑을 이용한 통계 구해오기 =============================================================================================================================
		List<HashMap<String, Integer>> list4 = new ArrayList<>();
		int key = 1;
		for(int i=1; i<=6; i++) {
			HashMap<String, Integer> hm = new HashMap<>();
			hm.put("key", key);
			hm.put("value", i);
			list4.add(hm);
			if(i % 2 == 0) key++;
		}
		list4.forEach((data)->{
			/*
			 * [출력결과]
			 * [ key, value ] : 1 / 1
			 * [ key, value ] : 1 / 2
			 * [ key, value ] : 2 / 3
			 * [ key, value ] : 2 / 4
			 * [ key, value ] : 3 / 5
			 * [ key, value ] : 3 / 6
			 * */
			System.out.println("[ key, value ] : "+data.get("key")+" / "+data.get("value"));
		});

		// 합계 구해오기
		Map<Integer, Integer> group4 = list4.stream()
				.collect( Collectors.groupingBy(
													hm-> hm.get("key"), 
													Collectors.summingInt(hm->hm.get("value"))
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
													hm-> hm.get("key"),
													Collectors.averagingInt(hm-> hm.get("value"))
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
		Map<Object, Object> group6 = list4.stream()
				.collect( Collectors.groupingBy(
													hm-> hm.get("key"),
							                        Collectors.collectingAndThen(
							                        		Collectors.minBy(Comparator.comparingInt(h -> h.get("value"))),
							                        		//Collectors.maxBy(Comparator.comparingInt(h -> h.get("value"))),
							                                optional -> optional.map(HashMap::new).orElse(new HashMap<>())
							                        )
												)
						);
		group6.forEach((key1,value1)->{
			/*
			 * [minBy출력결과]
			 * [ key, value ] : 1 / {key=1, value=1}
			 * [ key, value ] : 2 / {key=2, value=3}
			 * [ key, value ] : 3 / {key=3, value=5}
			 * [maxBy출력결과]
			 * [ key, value ] : 1 / {key=1, value=2}
			 * [ key, value ] : 2 / {key=2, value=4}
			 * [ key, value ] : 3 / {key=3, value=6}
			 * */
			System.out.println("[ key, value ] : "+key1+" / "+value1);
		});
		
		// 그룹별 통계 정보 구해오기
		Map<Integer, IntSummaryStatistics> group7 = list4.stream()
				.collect( Collectors.groupingBy(
													hm-> hm.get("key"),
													Collectors.summarizingInt(hm-> hm.get("value"))
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
}