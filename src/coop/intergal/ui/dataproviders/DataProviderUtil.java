package coop.intergal.ui.dataproviders;

import java.util.function.Function;
import java.util.function.Supplier;

import com.vaadin.flow.component.ItemLabelGenerator;

public class DataProviderUtil {

	public static <S, T> T convertIfNotNull(S source, Function<S, T> converter) {
		return convertIfNotNull(source, converter, () -> null);
	}

	public static <S, T> T convertIfNotNull(S source, Function<S, T> converter, Supplier<T> nullValueSupplier) {
		return source != null ? converter.apply(source) : nullValueSupplier.get();
	}

	public static <T> ItemLabelGenerator<T> createItemLabelGenerator(Function<T, String> converter) {
		return item -> convertIfNotNull(item, converter, () -> "");
	}

//	public static String convertIfNotNull(Long modelValue, Object converter) {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
