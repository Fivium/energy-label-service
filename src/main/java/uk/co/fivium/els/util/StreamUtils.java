package uk.co.fivium.els.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import uk.co.fivium.els.model.RatingClass;
import uk.co.fivium.els.model.RatingClassRange;

public class StreamUtils {

  public static <T, K, U> Collector<T, ?, Map<K, U>> toLinkedHashMap(Function<? super T, ? extends K> keyMapper,
                                                                     Function<? super T, ? extends U> valueMapper) {
    return Collectors.toMap(
        keyMapper,
        valueMapper,
        (u, v) -> {
          throw new IllegalStateException(String.format("Duplicate key %s", u));
        },
        LinkedHashMap::new
    );
  }

  // TODO move
  public static Map<String, String> ratingRangeToSelectionMap(RatingClassRange ratingClassRange) {
    return ratingClassRange.getApplicableRatings().stream()
        .collect(toLinkedHashMap(Enum::name, RatingClass::getDisplayValue));

  }

}