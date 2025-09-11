public class Calculator {
    static double e= 300_000; // Km/sec
    /* Freq in khz */
    public double FrequencyToLamda(double freq){
        return e/freq;
    }
    public double LamdaToFrequency(double lamda){
        return e/lamda;
    }
    // Non ricorsivo
    public int[] Fibonacci(int n){
        int[] fib = new int[n];
        fib[0]=0;
        if(n>1)
            fib[1]=1;
        for(int i=2;i<n;i++){
            fib[i]=fib[i-1]+fib[i-2];
        }
        return fib;
    }

    //Ricorsivo
    public int FibonacciR(int n){
        if(n==0) return 0;
        if(n==1) return 1;
        return FibonacciR(n-1)+FibonacciR(n-2);
    }

    public int[] MinMaxMean(int[] data){
        int[] result = new int[3];
        int sum=0;
        result[0]=data[0]; // min
        result[1]=data[0]; // max
        for(int val : data){
            sum+=val;
            if(val<result[0])
                result[0]=val;
            if(val>result[1])
                result[1]=val;
        }
        result[2]=sum/data.length; // mean
        return result;
    }
    
    public double WattToDBm(double watt){
        return 10*Math.log10(watt*1000);
    }
}
