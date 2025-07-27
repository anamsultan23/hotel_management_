 import java.sql.DriverManager;
 import java.sql.Connection;
 import java.sql.SQLException;
 import java.sql.Statement;
 import java.sql.ResultSet;
 import java.util.Scanner;
    

 public class hotel_reservation {
      private static final String DB_URL = "jdbc:mysql://localhost:3306/hotel_db";
      
      private static final String USER = "root";
      
      private static final String PASS = "Anamsultan@123";
 

      public static void main(String[] args) throws SQLException , ClassNotFoundException{
      try {
            Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage()); 
      }
      try {
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                  while(true){
                        System.out.println();
                        System.out.println("HOTEL MANAGEMENT SYSTEM");
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("1. Reserve a room ");
                        System.out.println("2. View all reservations");
                        System.out.println("3. Get a room number");
                        System.out.println("4. Update a reservation");
                        System.out.println("5. Delete a reservation");         
                        System.out.println("0. Exit");  
                                 
                        System.out.print("Enter your choice: ");
                        int choice = scanner.nextInt();
                        switch(choice){
                              case 1:
                                    reserveRoom(connection, scanner);
                                    break;
                              case 2:
                                    viewReservations(connection , scanner);
                                    break;
                              case 3:
                                    getRoomNumber(connection, scanner);
                                    break;
                              case 4:
                                    updateReservation(connection, scanner);
                                    break;
                              case 5:
                                    deleteReservation(connection, scanner);
                                    break;
                               case 0:
                                    exit();
                                     scanner.close();
                                     return;
                              default:
                        System.out.println("Invalid choice. Please try again.");
                  }
            }
      } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
      } catch (InterruptedException e) {
            throw new RuntimeException(e);
      }
      }

      private static void reserveRoom(Connection connection, Scanner scanner) {
            try {
                  System.out.print("Enter guest name: ");
                  String GuestName = scanner.next();
                  scanner.nextLine(); // Consume newline
                  System.out.print("Enter room number: ");
                  int roomNumber = scanner.nextInt();
                  System.out.print("Contact number: ");
                  String contactNumber = scanner.next();

                  String sql = "INSERT INTO reservations (guest_name, room_number, contact_number) " 
                               + "VALUES ('" + GuestName + "', " + roomNumber + ", '" + contactNumber + "')";

                 try( Statement statement = connection.createStatement()) {
                        int rowsAffected = statement.executeUpdate(sql);
                        if (rowsAffected > 0) {
                              System.out.println("Room reserved successfully.");
                        } else {
                              System.out.println("Failed to reserve room.");
                        }
                   }

             } catch (SQLException e) {
                  e.printStackTrace();
            }
      }


      public static void viewReservations(Connection connection, Scanner scanner) throws SQLException {
            String sql = "SELECT reservation_id, guest_name, room_number, contact_number , reservation_date FROM reservations";
            try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {


                  System.out.println("Current Reservations:");
                  System.out.println("+-----------------------------------------------------------------------------------------------------------+");
                  System.out.println("|  Reservation ID    |    Guest Name    |   Room Number   |    Contact Number    |    Reservation Date    |");
                  System.out.println("+-----------------------------------------------------------------------------------------------------------+");


                  while (resultSet.next()) {
                        int reservationId = resultSet.getInt("reservation_id");
                        String guestNAme = resultSet.getString("guest_name");
                        int roomNumber = resultSet.getInt("room_number");
                        String contactNumber = resultSet.getString("contact_number");
                        String reservationDate = resultSet.getString("reservation_date");

                        System.out.printf("|  %-17d |  %-15s |  %-14d |  %-20s |  %-20s |\n",
                                          reservationId, guestNAme, roomNumber, contactNumber, reservationDate);

                        
                  
                  System.out.println("+-----------------------------------------------------------------------------------------------------------+");} 
                }   
            }   
                

            private static void getRoomNumber(Connection connection, Scanner scanner) {
                  try {
                        System.out.print("Enter reservation ID ");
                        int reservationId = scanner.nextInt();
                        System.out.println("Enter guest Name ");
                        String guestName = scanner.next();

                        String sql = "SELECT room_number FROM reservations WHERE reservation_id = " + reservationId + " AND guest_name = '" + guestName + "'";

                        try (Statement statement = connection.createStatement();
                             ResultSet resultSet = statement.executeQuery(sql)) {
                              
                              if (resultSet.next()) {
                                    int roomNumber = resultSet.getInt("room_number");
                                    System.out.println("Room number for reservation ID " + reservationId + " is: " + roomNumber);
                              } else {
                                    System.out.println("No reservation found with the given ID and guest name.");
                              }
                        }
                  } catch (SQLException e) {
                        e.printStackTrace();
                  


            }
     }


     private static void updateReservation(Connection connection, Scanner scanner) {
            try {
                  System.out.print("Enter reservation ID to update: ");
                  int reservationId = scanner.nextInt();
                  scanner.nextLine(); // Consume newline

                  if(reservationId <= 0) {
                        System.out.println("Invalid reservation ID.");
                        return;
                  }

                  System.out.print("Enter new guest name");
                  String newGuestName = scanner.nextLine();
                  System.out.print("Enter new room number: ");
                  int newRoomNumber = scanner.nextInt();
                  System.out.print("Enter new contact number: ");
                  String newContactNumber = scanner.next();


                  String sql = "UPDATE reservations SET guest_name = '" + newGuestName + "', room_number = " + newRoomNumber + ", contact_number = '" + newContactNumber + "' WHERE reservation_id = " + reservationId;
                  try (Statement statement = connection.createStatement()) {
                        int rowsAffected = statement.executeUpdate(sql);
                        if (rowsAffected > 0) {
                              System.out.println("Reservation updated successfully.");
                        } else {
                              System.out.println("No reservation found with the given ID.");
                        }
                  }
            } catch (SQLException e) {
                  e.printStackTrace();
            } }
             
            
     
              
      public static void deleteReservation(Connection connection, Scanner scanner) {
            try {
                  System.out.print("Enter reservation ID to delete: ");
                  int reservationId = scanner.nextInt();

                  if(reservationId <= 0) {
                        System.out.println("Invalid reservation ID.");
                        return;
                  }

                  String sql = "DELETE FROM reservations WHERE reservation_id = " + reservationId;

                  try (Statement statement = connection.createStatement()) {
                        int rowsAffected = statement.executeUpdate(sql);
                        if (rowsAffected > 0) {
                              System.out.println("Reservation deleted successfully.");
                        } else {
                              System.out.println("No reservation found with the given ID.");
                        }
                  }

            } catch (SQLException e) {
                  e.printStackTrace();
            }  
      
      }



      public static void exit() throws InterruptedException {
      System.out.print("Exiting System");
      int i = 5;

      while(i != 0) {
            System.out.print(".");
            Thread.sleep(400);
            i--;
      }
      System.out.println();
      System.out.println("Thank you for using hotel reservation system");


      }
}


 



 
