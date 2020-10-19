 class Main {
	public static void main(String[] args) {
		
		//Generate two sets of random DataPoints, one being the training data set, one being the test data set.
		DataPoint[] traindata = new DataPoint[4];
		trainData[0] = new DataPoint(0, 0, "red", "yes");
		trainData[1] = new DataPoint(1, 0, "red", "yes");
		trainData[2] = new DataPoint(1, 1, "red", "no");
		trainData[3] = new DataPoint(2, 2, "blue", "no");
		
		DataPoint[] testData = new DataPoint[1];
		testData[0] = new DataPoint(1, 2, "blue", "");
		
		
		//Instantiate a DummyModel class, train and test with your random data and compute the precision and accuracy.
		DummyModel firstTry = new DummyModel();
		firstTry.train(trainData);
		firstTry.test(trainData);
		firstTry.getAccuracy(trainData);
		firstTry.getPrecision(trainData);
		
		
	}




}
