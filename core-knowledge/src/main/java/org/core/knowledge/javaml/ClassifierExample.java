/**
 * 
 */
package org.core.knowledge.javaml;

import java.io.File;
import java.io.IOException;

import net.sf.javaml.classification.Classifier;
import net.sf.javaml.classification.KNearestNeighbors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.data.FileHandler;

/**
 * core-knowledge
 * <description></description>
 * @author Johnny Liu
 * @date 2019-10-23
 */
public class ClassifierExample {

	public static void main(String[] args) throws IOException {
		// 1) initialize a classifier
		Dataset data = FileHandler.loadDataset(new File("E:/javaml/example/iris.data"), 4, ",");
		Classifier knn = new KNearestNeighbors(5);
		// 2) train it with some data
		knn.buildClassifier(data);
		// 3) use it to classify new instances
		Dataset dataForClazz = FileHandler.loadDataset(new File("E:/javaml/example/bezdekIris.data"), 4, ",");
		// 4) counters for corrent and wrong predictions
		int correct = 0, wrong = 0;
		for(Instance inst : dataForClazz) {
			Object predictedClassValue = knn.classify(inst);
			Object realClassValue = inst.classValue();
			if(predictedClassValue.equals(realClassValue)) {
				correct++;
			}else {
				wrong++;
			}
		}
		// 5) correct:145,wrong:5
		System.out.println("correct:"+correct+",wrong:"+wrong);
	}

}
