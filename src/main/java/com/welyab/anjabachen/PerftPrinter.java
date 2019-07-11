package com.welyab.anjabachen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PerftPrinter {
	
	private static final String COLUMN_SPACER = "  ";
	
	private static final List<String> COLUMN_NAMES = Arrays.asList(
		"Depth",
		"Nodes",
		"Captures",
		"En passats",
		"Castles",
		"Promotions",
		"Checks",
		"Dis_checks",
		"Dou_checks",
		"Checkmates",
		"Stalemates"
	);
	
	public void print(List<PieceMovementMeta> metas) {
		List<List<String>> values = metas.stream().map(this::metaToString).collect(Collectors.toList());
		IntStream.range(0, values.size()).forEach(l -> values.get(l).add(0, Integer.toString(l + 1)));
		values.add(0, COLUMN_NAMES);
		Map<Integer, Integer> widthByColumn = widthByColumn(values);
		values.forEach(l -> printList(l, widthByColumn));
	}
	
	private Map<Integer, Integer> widthByColumn(List<List<String>> values) {
		Map<Integer, Integer> map = new HashMap<>();
		for (int line = 0; line < values.size(); line++) {
			for (int column = 0; column < values.get(line).size(); column++) {
				map.put(
					column,
					Integer.max(
						map.getOrDefault(column, 0),
						values.get(line).get(column).length()
					)
				);
			}
		}
		return map;
	}
	
	private void printList(List<String> list, Map<Integer, Integer> widthByColumn) {
		for (int i = 0; i < list.size(); i++) {
			String s = normalize(list.get(i), widthByColumn.get(i));
			if (i > 0) {
				System.out.print(COLUMN_SPACER);
			}
			System.out.print(s);
		}
		System.out.println();
	}
	
	private String normalize(String s, int width) {
		StringBuilder builder = new StringBuilder(width);
		int x = width - s.length();
		for (int i = 0; i < x; i++) {
			builder.append(' ');
		}
		return builder.append(s).toString();
	}
	
	private List<String> metaToString(PieceMovementMeta meta) {
		List<String> list = new ArrayList<>();
		list.add(Integer.toString(meta.getTotalMovements()));
		list.add(Integer.toString(meta.getCaptureCount()));
		list.add(Integer.toString(meta.getEnPassantCount()));
		list.add(Integer.toString(meta.getCastlingsCount()));
		list.add(Integer.toString(meta.getPromotionCount()));
		list.add(Integer.toString(meta.getCheckCount()));
		list.add(Integer.toString(meta.getDiscoveryCheckCount()));
		list.add(Integer.toString(meta.getDoubleCheckCount()));
		list.add(Integer.toString(meta.getCheckmateCount()));
		list.add(Integer.toString(meta.getStalemateCount()));
		return list;
	}
}
