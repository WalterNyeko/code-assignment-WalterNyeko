Please provide review-comments for the code below:

```
@Component
public class MyAction {
    //kindly use abstraction by making this global variable private
    public boolean debug = true;
    @Autowired
    public DataSource ds;
    //instead of passing several arguments to getCustomer, you could create a model class that has all the
    //attributes passed as arguments to getCustomer, such that getCustomer takes only one parameter, the model class you created
    public Collection getCustomers(String firstName, String lastName, String address, String zipCode, String city) throws SQLException {
        Connection conn = ds.getConnection();
        //kindly check through the where clause of this sql statement, where 1=1 seems not to be valuable
        String query = new String("SELECT * FROM customers where 1=1");
        //It's good that you have validated the values, these validations will only check nullness though, check if these values are not blank or empty also
        if (firstName != null) {
            query = query + " and first_name = '" + firstName + "'";
        }
        //All these validations going down are repeated, you are validating firstName instead of lastName
        if (firstName != null) {
        //You are assigning value of firstName to the lastName, please correct.
            query = query + " and last_name = '" + firstName + "'";
        }
        if (firstName != null) {
            query = query + " and address = '" + address + "'";
        }
        if (firstName != null) {
            query = query + " and zip_code = '" + zipCode + "'";
        }
        if (firstName != null) {
            query = query + " and city = '" + city + "'";
        }
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        //this would now be List<Customer> if you created the Customer model
        List customers = new ArrayList();
        while (rs.next()) {
            Object[] objects = new Object[] { rs.getString(1), rs.getString(2) };
            if (debug) print(objects, 4);
            customers.add(new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
        }
        return customers;
    }

    public void print(Object[] s, int indent) {
        for (int i=0; i<=indent; i++) System.out.print(' ');
        printUpper(s);
    }

    public static void printUpper(Object [] words){
        int i = 0;
        try {
        //this loop seems to be endless, it might run forever and drain resources, please find way of stoping it
            while (true){
            //here, incase i is now too big beyond the number of words in the words[], then words[i].getClass() will throw NullPointerException, 
            //you should consider checking if words[i] is not null before calling getClass() on it
                if (words[i].getClass() == String.class) {
                //you have a typo, there are two semi colon
                    String so = (String)words[i];;
                    so = so.toUpperCase();
                    System.out.println(so);
                }
                i++;
            }
        } catch (IndexOutOfBoundsException e) {
        //Iteration gets completed within the try-block, not in the catch-block. 
        //Incase you were planning to implement logic related to -iteration complete- then this is the wrong place, do that inside the try-block. 
        //Here you should be handling errors instead. This review is a defensive review though, juts in case your comment below is depicting what you intend to do in this area
            //iteration complete
        }
    }
}
```
