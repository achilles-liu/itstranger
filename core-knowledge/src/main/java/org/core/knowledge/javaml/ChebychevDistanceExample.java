/**
 * 
 */
package org.core.knowledge.javaml;

import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.distance.ChebychevDistance;
import net.sf.javaml.distance.DistanceMeasure;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-10-25
 */
public class ChebychevDistanceExample {

	public static void main(String[] args) {
		double[] x_point = {1,2};
		double[] y_point = {-1,0};
		DenseInstance x = new DenseInstance(x_point);
		DenseInstance y = new DenseInstance(y_point);
		DistanceMeasure dm = new ChebychevDistance();
		double d_chess = dm.measure(x, y);
		// out: d_checss => 2.0
		System.out.println("d_checss => "+d_chess);
	}

}
