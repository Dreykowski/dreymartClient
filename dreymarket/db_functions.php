<?php

class DB_Functions{
    private $conn;

    function __construct()
    {
        require_once 'db_connect.php';
        $db = new DB_Connect();
        $this->conn = $db->connect();
    }
    function __destruct()
    {
        // TODO: Implement __destruct() method.
    }
    /*
     * Check user exist
     * return true/false
     */
    function checkExistUser($phone){
        $stmt = $this->conn->prepare("SELECT * FROM User WHERE Phone=?");
        $stmt->bind_param("s", $phone);
        $stmt->execute();
        $stmt->store_result();

        if($stmt->num_rows > 0)
        {
            $stmt->close();
            return true;
        }
        else
        {
            $stmt->close();
            return false;
        }}
    /*
     * Register new user
     * return user object if user was created
     * return false and show error message if have exception
     */


    public function registerNewUser($phone, $name, $birthdate, $address){
        $stmt = $this->conn->prepare("INSERT INTO User (Phone, Name, Birthdate, Address) VALUES(?,?,?,?)");
        $stmt->bind_param("ssss",$phone,$name,$birthdate,$address);
        $result=$stmt->execute();
        $stmt->close();

        if ($result)
        {
            $stmt=$this->conn->prepare("SELECT * FROM User WHERE Phone = ?");
            $stmt->bind_param("s", $phone);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $user;
        }
        else
        
            return false;}

    /*
     * Get User Information
     * return User object if user exists
     * return NULL is user is not exists
     */
    public function getUserInformation($phone)
    {
        $stmt = $this->conn->prepare("SELECT * FROM User WHERE Phone=?");
        $stmt->bind_param("s",$phone);

        if($stmt->execute())
        {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $user;
        }
    else
        return NULL;
    }


    /*
     * Get Banners
     * return List of Banners
     */
    public function getBanners()
    {
        //Select 3 newest banners
        $result = $this->conn->query("SELECT * FROM banner ORDER BY ID LIMIT 3");

        $banners = array();

        while ($item = $result->fetch_assoc()) 
            $banners[] = $item;

          return $banners;  
        
    }


    /*
     * Get Menu
     * return List of Menu
     */
    public function getMenu()
    {
        //Select 3 newest banners
        $result = $this->conn->query("SELECT * FROM Menu");

        $menu = array();

        while ($item = $result->fetch_assoc()) 
            $menu[] = $item;

          return $menu;  
        
    }    

    /*
     * Get Drinks base Menu ID
     * return List of Drinks
     */
    public function getBarangByMenuID($menuId)
    {
        $query = "SELECT * FROM Barang WHERE MenuId='".$menuId."'";
        $result = $this->conn->query($query);

        $barangs = array();

        while ($item = $result->fetch_assoc()) 
            $barangs[] = $item;

          return $barangs;
            
        
    }

    /*
     * Update Foto profil
     * return true or false
     */

    public function updateAvatar($phone, $fileName)
    {
        return $result = $this->conn->query("UPDATE user SET avatarUrl='$fileName' WHERE Phone='$phone' ");        
    }

    /*
     * mendapatkan semua barang
     * return list barang
     */

    public function getAllBarangs()
    {
        $result = $this->conn->query("SELECT * FROM barang WHERE 1") or die($this->conn->error);

        $barangs = array();
        while ($item = $result->fetch_assoc())
            $barangs[] = $item;
        return $barangs;
    }

/*
     * INSERT NEW ORDER
     * return TRUE or FALSE
     */

    public function insertNewOrder($orderPrice, $orderComment, $orderAddress, $orderDetail, $userPhone, $paymentMethod)
    {
        $stmt = $this->conn->prepare("INSERT INTO `order`(`OrderDate`, `OrderStatus`, `OrderPrice`, `OrderDetail`, `OrderComment`, `OrderAddress`, `UserPhone`, `PaymentMethod`) VALUES (NOW(),0,?,?,?,?,?,?)") or die ($this->conn->error);
        $stmt->bind_param("ssssss", $orderPrice, $orderDetail, $orderComment, $orderAddress, $userPhone, $paymentMethod);
        $result = $stmt->execute();
        $stmt->close();

        if ($result){
          $stmt = $this->conn->prepare("SELECT * FROM `order` WHERE `UserPhone`=? ORDER BY OrderId DESC LIMIT 1")
          or die ($this->conn->error);
          $stmt->bind_param("s",$userPhone);
          $stmt->execute();
          $order = $stmt->get_result()->fetch_assoc();
          $stmt->close();
          return $order;
        }
          
        else
          return false;
    }

    /*
        Insert Menu Baru(Category)
    */

    public function insertNewCategory($name, $imgPath)
    {
        $stmt = $this->conn->prepare("INSERT INTO `menu`(`Name`, `Link`) VALUES (?,?)") or die ($this->conn->error);
        $stmt->bind_param("ss", $name, $imgPath);
        $result = $stmt->execute();
        $stmt->close();

        if ($result)
          return true;
        else
          return false;
    }

    /*
      UPDATE Kategori
      Return TRUE or False
    */

    public function updateCategory($id, $name, $imgPath)
    {
      return $result = $this->conn->query("UPDATE menu SET Link='$imgPath', Name='$name' WHERE ID='$id' ");
      

      
    }
    /*  
      UPDATE Kategori
      Return TRUE or False
    */

    public function deleteCategory($id)
    {
      return $result = $this->conn->query("DELETE FROM menu WHERE ID='$id' ");
      
    }

    /*  
      MENDAPAT SEMUA PESANAN
      Return TRUE or False
    */
      public function getOrderByStatus($userPhone, $status)
      {
        $query = "SELECT * FROM `order` WHERE OrderStatus = '".$status."' AND UserPhone = '".$userPhone."'";
        $result = $this->conn->query($query) or die ($this->conn->error);

        $orders = array();
        while ($order = $result->fetch_assoc())
          $orders[] = $order;
        return $orders;

        
      }

      

      /*
        Insert Menu Baru(Category)
    */

    public function insertNewBarang($name, $imgPath, $price, $menuId)
    {
        // $stmt = $this->conn->prepare("INSERT INTO `barang`(`Name`, `Link`, `Price`, `MenuId`) VALUES (?,?,?,?)") or die ($this->conn->error);
        // $stmt->bind_param("ssss", $name, $imgPath, $price, $menuId);
        // $result = $stmt->execute();
        // $stmt->close();

        $stmt = $this->conn->prepare("INSERT INTO `barang`(`Name`, `Link`, `Price`, `MenuId`) VALUES (?,?,?,?)") or die ($this->conn->error);
        $stmt->bind_param("ssss", $name, $imgPath, $price, $menuId);
        $result = $stmt->execute();
        $stmt->close();

        if ($result)
          return true;
        else
          return false;
    }

    /*
      UPDATE Kategori
      Return TRUE or False
    */

    public function updateProduct($id, $name, $imgPath, $price, $menuId)
    { 
      return $result = $this->conn->query("UPDATE barang SET Link='$imgPath', Name='$name', Price='$price', MenuId='$menuId' WHERE ID='$id' ");
      

      
    }
    /*  
      UPDATE Kategori
      Return TRUE or False
    */

    public function deleteProduct($id)
    {
      return $result = $this->conn->query("DELETE FROM barang WHERE ID='$id' ");
      
    }

    /*  
      MENDAPAT SEMUA PESANAN
      Return TRUE or False
    */
      public function getOrderServerByStatus($status)
      {
        $query = "SELECT * FROM `order` WHERE OrderStatus = '".$status."'";
        $result = $this->conn->query($query) or die ($this->conn->error);

        $orders = array();
        while ($order = $result->fetch_assoc())
          $orders[] = $order;
        return $orders;

        
      }

      /*  
      MENDAPAT SEMUA PESANAN
      Return TRUE or False
    */
      public function insertToken($phone, $token, $isServerToken)
      {
        
        $stmt = $this->conn->prepare("INSERT INTO `token` (`phone`,`token`, `isServerToken`) VALUES (?,?,?) ON DUPLICATE KEY UPDATE token=?, isServerToken=? ") or die ($this->conn->error);
        $stmt->bind_param("sssss", $phone, $token, $isServerToken, $token, $isServerToken);
        $result = $stmt->execute();
        $stmt->close();

        if ($result) 
        {
          $stmt=$this->conn->prepare("SELECT * FROM token WHERE phone=?");
          $stmt->bind_param("s", $phone);
          $stmt->execute();
          $user = $stmt->get_result()->fetch_assoc();
          $stmt->close();
          return $user;
        }
      else
      {
        return false;
      }


      }

      /*
     * Membatalkan pesanan
     * return true or false
     */

      public function cancelOrder($orderId, $userPhone)
      {
        $stmt = $this->conn->prepare("UPDATE `order` SET `OrderStatus`=-1 WHERE `OrderId`=? AND `UserPhone`=?") or die ($this->conn->error);
        $stmt->bind_param("ss",$orderId, $userPhone);
        $result = $stmt->execute() or die ($stmt->error);
        return $result;
      }

      /*
     * Toko Terdekat
     * return List Toko or false
     */

      public function getNearbyStore($lat, $lng)
      {
        $result = $this->conn->query("SELECT id, name, lat, lng, ROUND(111.045 * DEGREES(ACOS(COS(RADIANS($lat))"
        ."* COS(RADIANS(lat))"
          ."* COS(RADIANS(lng) - RADIANS($lng))"
          ."+ SIN(RADIANS($lat))"
          ."* SIN(RADIANS(lat)))),2)"
          ." AS distance_in_km FROM store"
          ." ORDER BY distance_in_km ASC") or die ($this->conn->error);
        $stores = array();
        while ($store = $result->fetch_assoc())
          $stores[] = $store;
        return $stores;
      }


/*
     * ServerUpdateStatus
     * return List Toko or false
     */

      public function updateOrderStatus($phone, $order_id, $status)
      {
        $stmt = $this->conn->prepare("UPDATE `order` SET `OrderStatus` =? WHERE `UserPhone`=? AND `OrderId`=?")
        or die ($this->conn->error);
        $stmt->bind_param("sss", $status, $phone, $order_id);
        $result = $stmt->execute();
        return $result;
      }
/*
     * get token
     * return get token or false
     */
      public function getToken($phone, $isServerToken)
      {
        $stmt = $this->conn->prepare("SELECT * FROM `token` WHERE phone=? AND isServerToken=?") or die ($this->conn->error);
        $stmt->bind_param("ss", $phone, $isServerToken);
        $result = $stmt->execute();
        $token = $stmt->get_result()->fetch_assoc();
        $stmt->close();
        return $token;
      }



}

?>
