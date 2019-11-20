/**
 * 
 */
package org.core.knowledge.service;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rholder.retry.Retryer;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-11-20
 */
@Slf4j
@Service
public class RetryService{
	
	@Data
	public static class Receipt {
		protected int code;
		protected String message;
		protected Object data;
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Receipt other = (Receipt) obj;
			if (code != other.code)
				return false;
			return true;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + code;
			return result;
		}
	}
	
	public static class Receipts {
		public static Receipt failureReceit() {
			Receipt rec = new Receipt();
			rec.setCode(-1);
			rec.setMessage("failure");
			return rec;
		}
		
		public static Receipt successReceit() {
			Receipt rec = new Receipt();
			rec.setCode(0);
			rec.setMessage("success");
			return rec;
		}
	}
	
	public static class Proxy{
		public static Receipt wrap(Retryer<Receipt> retryer,Function<Object, Receipt> func,Object...params) {
			try {
				return retryer.call(() -> {
					log.info("~~~call flag~~~");
					return func.apply(params);
				});
			} catch (Exception e) {
				log.error("{}",e.getMessage());
				return Receipts.failureReceit();
			}
		}
	}
	
	private @Autowired Retryer<Receipt> retryer;
	
	public String testRetry(String name) {
		Receipt rec = Proxy.wrap(retryer, (i) -> {
			Receipt rec0 = Receipts.failureReceit();
			rec0.setData(i);
			return rec0;
		},name);
		log.info("{}",rec.getMessage());
		return "Hi,"+rec.getMessage();
	}

}
