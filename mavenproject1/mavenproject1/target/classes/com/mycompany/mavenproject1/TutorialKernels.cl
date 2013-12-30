__kernel void add_floats(__global const float* a, __global const float* b, __global float* out, int n) 
{
    int i = get_global_id(0);
    if (i >= n)
        return;

    out[i] = a[i] + b[i];
}

__kernel void fill_in_values(__global float* a, __global float* b, int n) 
{
    int i = get_global_id(0);
    if (i >= n)
        return;

    a[i] = cos((float)i);
    b[i] = sin((float)i);
}

__kernel void testKernel(__global int* ursprung,__global int* wegeKarte,__global int* besucht,__global int* vorgaenger, __global int* abbruch, int breite, int n) 
{
    int i = get_global_id(0);
    if (i > n)
        return;


    if(besucht[i] == 1)
    {
        if((besucht[i+1] == 0) & (ursprung[i+1] == 0))
        {
            if(!((i+1)>n))
            {
                if((i+1)%breite > 0)
                { 
                    besucht[i+1] = 1;
                    wegeKarte[i+1] = wegeKarte[i]+1;
                    if (vorgaenger[i+1] > i | vorgaenger[i+1] == -1)
                        vorgaenger[i+1] = i;
                    abbruch[0] = 0;
                }
            }
        }
        if((besucht[i-1]) == 0 & (ursprung[i-1] == 0))
        {
            if(!((i-1)< 0))
            {
                if((i)%breite > 0)
                { 
                    besucht[i-1] = 1;
                    wegeKarte[i-1] = wegeKarte[i]+1;
                    if (vorgaenger[i-1] > i | vorgaenger[i-1] == -1)
                        vorgaenger[i-1] = i;
                    abbruch[0] = 0;
                }
            }
        }
        if((besucht[i-breite] == 0) & (ursprung[i-breite] != 8))
        {
            if(!((i-breite) < 0))
            {
                besucht[i-breite] = 1;
                wegeKarte[i-breite] = wegeKarte[i]+1;
                if (vorgaenger[i-breite] > i | vorgaenger[i-breite] == -1)
                    vorgaenger[i-breite] = i;
                abbruch[0] = 0;
            }
        }
        if((besucht[i+breite] == 0) & (ursprung[i+breite] == 0))
        {
            if(!((i+breite)>n))
            {
                besucht[i+breite] = 1;
                wegeKarte[i+breite] = wegeKarte[i]+1;
                if (vorgaenger[i+breite] > i | vorgaenger[i+breite] == -1)
                    vorgaenger[i+breite] = i;
                abbruch[0] = 0;
            }
        }
    }
}