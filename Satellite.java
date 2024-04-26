public class Satellite
{
    private String sat_name = null;
    private String TLE_Line_1 = null;
    private String TLE_Line_2 = null;

    public Satellite(String name, String line1, String line2)
    {
        sat_name = name;
        TLE_Line_1 = line1;
        TLE_Line_2 = line2;
    }

    public String catalog_number()
    {
        return TLE_Line_1.substring(2, 7);
    }

    public String name()
    {
        return sat_name;
    }

    public int launch_year_yy()
    {
        return Integer.parseInt(TLE_Line_1.substring(9, 11));
    }

    public static int year_yyyy(int yy)
    {
        if (yy < 57)
            return yy + 2000;
        else
            return yy + 1900;
    }

    public int launch_year_yyyy()
    {
        return year_yyyy(launch_year_yy());
    }

    public String toString()
    {
        return catalog_number() + "    " + name();
    }

    public double mean_motion()
    {
        return Double.parseDouble(TLE_Line_2.substring(52, 63));
    }

    public double inclination()
    {
        return Double.parseDouble(TLE_Line_2.substring(8, 16));
    }

    public double eccentricity()
    {
        return Double.parseDouble("0." + TLE_Line_2.substring(26, 33));
    }

    public double period_min()
    {
        return 0.0;
    }
    public double apogee()
    {
        return 0.0;
    }
    public double perigee()
    {
        return 0.0;
    }
}