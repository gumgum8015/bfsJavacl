package com.mycompany.mavenproject1;

import com.nativelibs4java.opencl.*;
import com.nativelibs4java.opencl.CLMem.Usage;
import com.nativelibs4java.util.*;
import org.bridj.Pointer;
import java.nio.ByteOrder;
import java.io.IOException;

public class JavaCLTutorial1 {
    public static void main(String[] args) throws IOException 
    {
        CLContext context = JavaCL.createBestContext();
        CLQueue queue = context.createDefaultQueue();
        ByteOrder byteOrder = context.getByteOrder();
        
        // Testabfragen wie Groß der Speicher ist
        long size = queue.getDevice().getGlobalMemSize();
        long lsize = queue.getDevice().getLocalMemSize(); 
        
        
        // Variablen, welche das koordinatensystem darstellen
        int hoehe = 700;
        int breite = 700;
        int gesamt = breite*hoehe;
        
        // Benötigte karten initz9ialisieren
        Pointer<Integer> UrsprungsKarte = Pointer.allocateInts(gesamt).order(byteOrder);
        Pointer<Integer> WegeKarte = Pointer.allocateInts(gesamt).order(byteOrder);

        // Informationsarray für die berechnung und die Wegberechnung am Ende
        Pointer<Integer> besucht = Pointer.allocateInts(gesamt).order(byteOrder);    // Wegen der buffer 1 = true, 0 = false
        Pointer<Integer> vorgaenger = Pointer.allocateInts(gesamt).order(byteOrder);
        Pointer<Integer> abbruch = Pointer.allocateInts(gesamt).order(byteOrder);
   
                
        // Karte mit verschiedenen Bewegungspunkten füllen
        for (int i = 0; i < gesamt; i++) 
        {
            // Zufall UrsprungsKarte.set(i, myRandom(0,2));
            UrsprungsKarte.set(i, 0);
            vorgaenger.set(i,-1);
        }
        
        besucht.set(32,1);
        UrsprungsKarte.set(32,1);
        WegeKarte.set(32,0);
        vorgaenger.set(32,-2);
        
        UrsprungsKarte.set(15, 8);
        UrsprungsKarte.set(22, 8);
        UrsprungsKarte.set(23, 8);
        UrsprungsKarte.set(27, 8);
        UrsprungsKarte.set(29, 8);
        UrsprungsKarte.set(33, 8);
        UrsprungsKarte.set(35, 8);
        UrsprungsKarte.set(37, 8);
        UrsprungsKarte.set(39, 8);
        UrsprungsKarte.set(40, 8);
        UrsprungsKarte.set(41, 8);
        UrsprungsKarte.set(42, 8);
        UrsprungsKarte.set(43, 8);
        UrsprungsKarte.set(45, 8);
        UrsprungsKarte.set(47, 8);
        UrsprungsKarte.set(49, 8);
        UrsprungsKarte.set(55, 8);
        UrsprungsKarte.set(56, 8);
        UrsprungsKarte.set(57, 8);
        UrsprungsKarte.set(59, 8);
        UrsprungsKarte.set(60, 8);
        UrsprungsKarte.set(61, 8);
        UrsprungsKarte.set(63, 8);
        UrsprungsKarte.set(64, 8);
        UrsprungsKarte.set(65, 8);
        UrsprungsKarte.set(69, 8);
        UrsprungsKarte.set(71, 8);
        UrsprungsKarte.set(73, 8);
        UrsprungsKarte.set(77, 8);
        UrsprungsKarte.set(79, 8);
        UrsprungsKarte.set(85, 8);
        UrsprungsKarte.set(87, 8);
        UrsprungsKarte.set(89, 8);
        UrsprungsKarte.set(90, 8);
        UrsprungsKarte.set(91, 8);
        UrsprungsKarte.set(92, 8);
        UrsprungsKarte.set(93, 8);
        UrsprungsKarte.set(94, 8);
        UrsprungsKarte.set(95, 8);
        UrsprungsKarte.set(96, 8);
        UrsprungsKarte.set(97, 8);
        UrsprungsKarte.set(98, 8);
        UrsprungsKarte.set(99, 8);
        
                                        
       
        
        
        
        
        
        
        
        
        
        
        

        // Create OpenCL input buffers (using the native memory pointers aPtr and bPtr) :
        CLBuffer<Integer> ursprung_Buffer = context.createIntBuffer(Usage.Input, UrsprungsKarte);
        CLBuffer<Integer> besucht_Buffer = context.createIntBuffer(Usage.Input, besucht);
        CLBuffer<Integer> vorgaenger_Buffer = context.createIntBuffer(Usage.InputOutput, vorgaenger);
        
        // Create an OpenCL output buffer :
        CLBuffer<Integer> wege_Buffer = context.createIntBuffer(Usage.Output, WegeKarte);
        CLBuffer<Integer> abbruch_Buffer = context.createIntBuffer(Usage.InputOutput, abbruch);
        
        System.out.println("Hier gehts auf die Graka!");
        
        // Kernel auslesen und kompilieren lassen! TODO -> namen ändern!
        String src = IOUtils.readText(JavaCLTutorial1.class.getResource("TutorialKernels.cl"));
        CLProgram program = context.createProgram(src);

        // Get and call the kernel :
        CLKernel testKernel = program.createKernel("testKernel");
        testKernel.setArgs(ursprung_Buffer, wege_Buffer,besucht_Buffer,vorgaenger_Buffer,abbruch_Buffer,breite, gesamt);
        int[] globalSizes = new int[] { gesamt };
        
        CLEvent addEvt = null;

        int c = 0;
        do
        {
            abbruch.set(0,88888);                       // Flag neu setzten und Übertragen
            abbruch_Buffer.write(queue, abbruch, true);
            addEvt = testKernel.enqueueNDRange(queue, globalSizes);
            abbruch = abbruch_Buffer.read(queue, addEvt);
            c++;
            System.out.println("Durchläufe: " + c);
            System.out.println("Size: " + abbruch.getBytes().length);
        }while(abbruch.get(0) == 0);

        
        
        System.out.println("Hier gehts zurück!");
                
        Pointer<Integer> outPtr = wege_Buffer.read(queue, addEvt); // blocks until add_floats finished
        Pointer<Integer> outPtr2 = besucht_Buffer.read(queue, addEvt); // blocks until add_floats finished
        Pointer<Integer> outPtr3 = vorgaenger_Buffer.read(queue, addEvt); // blocks until add_floats finished
        /*
        for (int i = 0; i < gesamt && i < gesamt; i++)
        {
            System.out.print(" :: " + UrsprungsKarte.get(i));
            if(((i+1)%breite)==0)
                System.out.println();
        }

        System.out.println();
        System.out.println("###############");
        System.out.println();
        
        for (int i = 0; i < gesamt && i < gesamt; i++)
        {
            System.out.print(" :: " + outPtr.get(i));
            if(((i+1)%breite)==0)
                System.out.println();
        }
                
        System.out.println();
        System.out.println("###############");
        System.out.println();
        
        for (int i = 0; i < gesamt && i < gesamt; i++)
        {
            System.out.print(" :: " + outPtr2.get(i));
            if(((i+1)%breite)==0)
                System.out.println();
        }
        
        System.out.println();
        System.out.println("###############");
        System.out.println();
        
        for (int i = 0; i < gesamt && i < gesamt; i++)
        {
            System.out.print(" :: " + outPtr3.get(i));
            if(((i+1)%breite)==0)
                System.out.println();
        }
          
        // Weg von Punkt 88 zum Startpunkt 32!
        int actual = 88;
       while(vorgaenger.get(actual) != -2)
       {
           System.out.println(outPtr3.get(actual));
           actual = outPtr3.get(actual);
       }
        
        System.out.println("Global Mem size = " + size + " byte - Local Mem size :" + lsize + " bytes");
        */
        
        //context.cc        
    }
    
    
    public static int myRandom(int low, int high) 
    {
        return (int) (Math.random() * (high - low) + low);
    }
}