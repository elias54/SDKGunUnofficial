package SDK_Flasher.common;

public class LitBlock {
	
    private Point3d blockLocation;
    private int lightValues[];
    private int lightLevel;

    public LitBlock(Point3d sdkpoint3d, int ai[], int i)
    {
        blockLocation = sdkpoint3d;
        lightValues = ai;
        lightLevel = i;
    }

    public Point3d getBlockLocation()
    {
        return blockLocation;
    }

    public int[] getLightValues()
    {
        return lightValues;
    }

    public int getLightLevel()
    {
        return lightLevel;
    }

    public boolean equals(Object obj)
    {
        if (obj instanceof LitBlock)
        {
            return blockLocation.equals(((LitBlock)obj).blockLocation);
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return blockLocation.hashCode();
    }
}