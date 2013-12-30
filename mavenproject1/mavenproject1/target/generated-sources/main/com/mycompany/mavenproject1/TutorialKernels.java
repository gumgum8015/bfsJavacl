package com.mycompany.mavenproject1;
import com.nativelibs4java.opencl.CLAbstractUserProgram;
import com.nativelibs4java.opencl.CLBuffer;
import com.nativelibs4java.opencl.CLBuildException;
import com.nativelibs4java.opencl.CLContext;
import com.nativelibs4java.opencl.CLEvent;
import com.nativelibs4java.opencl.CLKernel;
import com.nativelibs4java.opencl.CLProgram;
import com.nativelibs4java.opencl.CLQueue;
import java.io.IOException;
/** Wrapper around the OpenCL program TutorialKernels */
public class TutorialKernels extends CLAbstractUserProgram {
	public TutorialKernels(CLContext context) throws IOException {
		super(context, readRawSourceForClass(TutorialKernels.class));
	}
	public TutorialKernels(CLProgram program) throws IOException {
		super(program, readRawSourceForClass(TutorialKernels.class));
	}
	CLKernel add_floats_kernel;
	public synchronized CLEvent add_floats(CLQueue commandQueue, CLBuffer<Float > a, CLBuffer<Float > b, CLBuffer<Float > out, int n, int globalWorkSizes[], int localWorkSizes[], CLEvent... eventsToWaitFor) throws CLBuildException {
		if ((add_floats_kernel == null)) 
			add_floats_kernel = createKernel("add_floats");
		add_floats_kernel.setArgs(a, b, out, n);
		return add_floats_kernel.enqueueNDRange(commandQueue, globalWorkSizes, localWorkSizes, eventsToWaitFor);
	}
	CLKernel fill_in_values_kernel;
	public synchronized CLEvent fill_in_values(CLQueue commandQueue, CLBuffer<Float > a, CLBuffer<Float > b, int n, int globalWorkSizes[], int localWorkSizes[], CLEvent... eventsToWaitFor) throws CLBuildException {
		if ((fill_in_values_kernel == null)) 
			fill_in_values_kernel = createKernel("fill_in_values");
		fill_in_values_kernel.setArgs(a, b, n);
		return fill_in_values_kernel.enqueueNDRange(commandQueue, globalWorkSizes, localWorkSizes, eventsToWaitFor);
	}
	CLKernel testKernel_kernel;
	public synchronized CLEvent testKernel(CLQueue commandQueue, CLBuffer<Integer > ursprung, CLBuffer<Integer > wegeKarte, CLBuffer<Integer > besucht, CLBuffer<Integer > vorgaenger, CLBuffer<Integer > abbruch, int breite, int n, int globalWorkSizes[], int localWorkSizes[], CLEvent... eventsToWaitFor) throws CLBuildException {
		if ((testKernel_kernel == null)) 
			testKernel_kernel = createKernel("testKernel");
		testKernel_kernel.setArgs(ursprung, wegeKarte, besucht, vorgaenger, abbruch, breite, n);
		return testKernel_kernel.enqueueNDRange(commandQueue, globalWorkSizes, localWorkSizes, eventsToWaitFor);
	}
}
