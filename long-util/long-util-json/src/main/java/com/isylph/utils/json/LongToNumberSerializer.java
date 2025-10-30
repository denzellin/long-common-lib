package com.isylph.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @Author Denzel Lin
 * @Date 2025/10/30 11:35
 * @Version 1.0
 */
public class LongToNumberSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        try {
            gen.writeNumber(value); // 或 Double.parseDouble(value)
        } catch (NumberFormatException e) {
            gen.writeNull(); // 如果不是数字就输出 null，防止 JSON 出错
        }
    }
}
