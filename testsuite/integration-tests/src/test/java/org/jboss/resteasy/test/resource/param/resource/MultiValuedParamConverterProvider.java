package org.jboss.resteasy.test.resource.param.resource;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;

public class MultiValuedParamConverterProvider implements ParamConverterProvider {

	@SuppressWarnings("unchecked")
	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
		if (MultiValuedParam.class.isAssignableFrom(rawType)) {
			ParamConverter<T> paramConverter = (ParamConverter<T>) getConverter(getType(genericType));
			return (ParamConverter<T>) (paramConverter != null ? new MultiValuedParamConverter(paramConverter) : null);
		} else if (MultiValuedCookieParam.class.isAssignableFrom(rawType)) {
			ParamConverter<T> paramConverter = (ParamConverter<T>) getConverter(getType(genericType));
			return (ParamConverter<T>) (paramConverter != null ? new MultiValuedCookieParamConverter(paramConverter)
					: null);
		}else if (MultiValuedPathParam.class.isAssignableFrom(rawType)) {
			ParamConverter<T> paramConverter = (ParamConverter<T>) getConverter(getType(genericType));
			return (ParamConverter<T>) (paramConverter != null ? new MultiValuedPathParamConverter(paramConverter)
					: null);
		}
		return (ParamConverter<T>) getConverter(rawType);
	}

	private static Class<?> getType(Type genericType) {
		ParameterizedType parameterizedType = (ParameterizedType) genericType;
		Type type = parameterizedType.getActualTypeArguments()[0];
		return (type instanceof Class) ? (Class<?>) type : null;
	}

	private static ParamConverter<?> getConverter(Class<?> rawType) {
		if (Date.class.isAssignableFrom(rawType)) {
			return new DateParamConverter();
		}
		return null;
	}

}
