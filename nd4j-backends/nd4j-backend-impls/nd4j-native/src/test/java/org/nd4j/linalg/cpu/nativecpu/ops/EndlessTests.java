package org.nd4j.linalg.cpu.nativecpu.ops;

import org.apache.commons.lang3.RandomUtils;
import org.bytedeco.javacpp.Pointer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.impl.accum.distances.CosineSimilarity;
import org.nd4j.linalg.api.ops.impl.transforms.RectifedLinear;
import org.nd4j.linalg.api.ops.impl.transforms.SoftMax;
import org.nd4j.linalg.api.ops.impl.transforms.arithmetic.AddOp;
import org.nd4j.linalg.factory.Nd4j;

import static org.junit.Assert.assertEquals;

/**
 * @author AlexDBlack
 * @author raver119@gmail.com
 */
@Ignore
public class EndlessTests {
    private static final int RUN_LIMIT = Integer.MAX_VALUE;

    @Before
    public void setUp() {
    }

    @Test
    public void testTransformsForeverSingle(){
        INDArray arr = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            Nd4j.getExecutioner().exec(new RectifedLinear(arr));
        }
    }

    @Test
    public void testTransformsForeverSingle2(){
        INDArray arr = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            Nd4j.getExecutioner().exec(new SoftMax(arr));
        }
    }

    @Test
    public void testTransformsForeverPairwise(){
        INDArray arr = Nd4j.ones(100,100);
        INDArray arr2 = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            Nd4j.getExecutioner().exec(new AddOp(arr,arr2,arr));
        }
    }

    @Test
    public void testAccumForeverFull(){
        INDArray arr = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            arr.sumNumber();
        }
    }

    @Test
    public void testAccumForeverMax(){
        INDArray arr = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            arr.maxNumber();
        }
    }

    @Test
    public void testAccumForeverMaxDifferent(){


        for (int i = 0; i < RUN_LIMIT; i++ ) {
            int rows = RandomUtils.nextInt(1, 500);
            int columns = RandomUtils.nextInt(1, 500);
            INDArray arr = Nd4j.ones(rows, columns);
            float res = arr.maxNumber().floatValue();

            assertEquals("Failed on rows: ["+rows+"], columns: ["+columns+"], iteration: ["+i+"]", 1.0f, res, 0.01f);
        }
    }

    @Test
    public void testAccumForeverAlongDimension(){
        INDArray arr = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            arr.sum(0);
        }
    }

    @Test
    public void testAccumForeverAlongDimensions(){
        INDArray arr = Nd4j.linspace(1, 10000, 10000).reshape(10, 10, 100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            arr.sum(0,1);
        }
    }

    @Test
    public void testIndexAccumForeverFull(){
        INDArray arr = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            Nd4j.argMax(arr,Integer.MAX_VALUE);
        }
    }

    @Test
    public void testStdDevForeverFull(){
        INDArray arr = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            arr.stdNumber();
        }
    }

    @Test
    public void testIndexAccumForeverAlongDimension(){
        INDArray arr = Nd4j.ones(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            Nd4j.argMax(arr,0);
        }
    }

    @Test
    public void testIndexAccumForeverAlongDimensions(){
        INDArray arr = Nd4j.linspace(1, 10000, 10000).reshape(10, 10, 100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            Nd4j.argMax(arr,0, 1);
        }
    }


    @Test
    public void testBroadcastForever(){
        INDArray arr = Nd4j.ones(100,100);
        INDArray arr2 = Nd4j.ones(1,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            arr.addiRowVector(arr2);
        }
    }

    @Test
    public void testScalarForever(){
        INDArray arr = Nd4j.zeros(100,100);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            arr.addi(1.0);
        }
    }

    @Test public void testReduce3(){
        INDArray first = Nd4j.ones(10,10);
        INDArray second = Nd4j.ones(10,10);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            Nd4j.getExecutioner().exec(new CosineSimilarity(first,second));
        }
    }

    @Test
    public void testReduce3AlongDim(){
        INDArray first = Nd4j.ones(10,10);
        INDArray second = Nd4j.ones(10,10);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            Nd4j.getExecutioner().exec(new CosineSimilarity(first,second),0);
        }
    }

    @Test
    public void testMmulForever(){
        INDArray first = Nd4j.zeros(10,10);
        INDArray second = Nd4j.zeros(10,10);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            first.mmul(second);
        }
    }

    @Test
    public void testAxpyForever(){
        INDArray first = Nd4j.zeros(10,10);
        INDArray second = Nd4j.zeros(10,10);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            Nd4j.getBlasWrapper().level1().axpy(100,1,first,second);
        }
    }

    @Test
    public void testConcatForever1() {
        INDArray[] arr = new INDArray[3];
        arr[0] = Nd4j.linspace(0,49,50).reshape('c',5,10);
        arr[1] = Nd4j.linspace(50,59,10);
        arr[2] = Nd4j.linspace(60,99,40).reshape('c',4,10);

        INDArray expected = Nd4j.linspace(0,99,100).reshape('c',10,10);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            INDArray actual = Nd4j.vstack(arr);
            assertEquals("Failed on [" + i + "] iteration",expected, actual);
            if (i % 500 == 0) {
                System.out.println("Iteration " + i + " passed, Mem: " + (Pointer.maxBytes() / 1024 / 1024));
            }
        }
    }

    @Test
    public void testConcatForever2() {
        INDArray expected = Nd4j.linspace(1,9,9).reshape('c',3,3);

        for (int i = 0; i < RUN_LIMIT; i++ ) {
            for(char order : new char[]{'c','f'}){
                Nd4j.factory().setOrder(order);

                INDArray arr1 = Nd4j.linspace(1,6,6).reshape('c',2,3);
                INDArray arr2 = Nd4j.linspace(7,9,3).reshape('c',1,3);

                INDArray merged = Nd4j.vstack(arr1,arr2);

                assertEquals("Failed on [" + i + "] iteration", expected, merged);
            }

            if (i % 500 == 0)
                System.out.println("Iteration " + i + " passed");
        }
    }

    @Test
    public void testConcatForever3() {
        for (int i = 0; i < RUN_LIMIT; i++) {
            INDArray arrays[] = new INDArray[4];
            for (int x = 0; x< 4; x++) {
                arrays[x] = Nd4j.rand(new int[]{1, 100, 100});
            }

            INDArray result = Nd4j.concat(0, arrays);
            result.muli(1/256f);

            if (i % 500 == 0) {
                System.out.println("Iteration " + i + " passed, Mem: " + (Pointer.totalBytes() / 1024 / 1024));
            }
        }
    }
}
