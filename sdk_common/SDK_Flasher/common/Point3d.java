package SDK_Flasher.common;

public class Point3d {

    public Object x;
    public Object y;
    public Object z;
    
    public Point3d(Object obj, Object obj1, Object obj2)
    {
        x = obj;
        y = obj1;
        z = obj2;
    }
    
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof Point3d))
        {
            return false;
        }
        else
        {
            Point3d sdkpoint3d = (Point3d)obj;
            return x == sdkpoint3d.x && y == sdkpoint3d.y && z == sdkpoint3d.z;
        }
    }

    public int hashCode()
    {
        int i = 1;
        i = i * 31 + x.hashCode();
        i = i * 31 + y.hashCode();
        i = i * 31 + z.hashCode();
        return i;
    }
	
}
