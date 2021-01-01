package com.example.orderservice.dao;

import com.example.orderservice.pojo.*;
import com.example.orderservice.vo.OrderDetailVo;
import com.example.orderservice.dto.DriverDetailDto;
import com.example.orderservice.vo.OrderWithDistanceVO;
import org.apache.ibatis.annotations.*;


import java.util.List;

@Mapper
public interface OrderMapper {
   //查找并返回所有订单详情
   List<OrderDetailVo> findAllDetail();

    /**
     *
     * @param order_id 订单id
     * @return 订单详情
     */
   OrderDetailVo findOrderDetailByOrderId(@Param("order_id") String order_id);


    /**
     *
     * @param user_id 用户id
     * @param type 订单种类
     * @return 订单详情信息
     */
   List<OrderDetailVo> findOrderDetailByUserId(String user_id,int type);

    /**
     *
     * @param user_id 用户id
     * @param type 订单种类
     * @param state 订单状态
     * @return 订单详情
     */
   List<OrderDetailVo> findOrderDetailByUserIdAndState(String user_id,int type,int state);

    /**
     *
     * @param lon 司机目的地经度
     * @param lat 司机目的地纬度
     * @return 距离司机目的地最近的乘客需求订单
     */
   List<OrderWithDistanceVO> findNearestOrders(Double lon, Double lat,int type);

   //根据订单id查找对应的评论
   Comment findCommentById(@Param("order_id") String order_id);

   //根据用户id查找用户
   Passenger findPassengerById(@Param("user_id") String user_id);

   //根据司机id返回司机
   Driver findDriverById(@Param("driver_id") String driver_id);

   //根据司机id返回对应车辆的全部信息
   Car findCarById(@Param("driver_id") String driver_id);

   //根据司机id返回司机全部详细信息（包括司机的车辆信息）
   DriverDetailDto findDriverDetailById(@Param("driver_id") String driver_id);

   //更新订单状态
   int takeOrder(@Param("order_id")String order_id,@Param("driver_id") String driver_id);

   int pickUp(@Param("order_id")String order_id);

   int finishOrder(@Param("order_id")String order_id);

   int saveComment(@Param("order_id")String order_id,@Param("score") Double score,@Param("content") String content);

   void deleteByOid(String order_id,int type);
   /* @Results(value = {
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "passengers", column = "user_id",
                    many = @Many(select="findPassengers")),
    })*/
    @Select("select * from soadb.order")
    List<Order> findAllOrders();

    @Select("select * from soadb.order where order_id = #{order_id}")
    Order findOrderById(String order_id);

    @Select("SELECT" +
            "*, "+
            "    ROUND(" +
            "        6378.138 * 2 * ASIN(" +
            "            SQRT(" +
            "                POW(" +
            "                    SIN( ( #{lat} * PI() / 180 - to_lat * PI() / 180 ) / 2 ) , 2 )" +
            "                +" +
            "                COS( #{lat} * PI( ) / 180 ) * COS( to_lat * PI( ) / 180 )" +
            "                * POW( SIN( ( #{lon} * PI() / 180 - to_lon * PI() / 180 ) / 2 ) , 2 )" +
            "            )" +
            "        ) * 1000" +
            "    ) AS distance " +
            "FROM soadb.order " +
            "ORDER BY distance ASC " +
            "LIMIT 20;")
    List<Order> findMatchOrders(@Param("lon") Double lon,@Param("lat") Double lat);
    @Insert("INSERT INTO soadb.order(order_id,type,state,user_id,passenger_num,datetime," +
            "from_name,to_name,from_lon,from_lat,to_lon,to_lat,driver_id,description) " +
            "VALUES (#{order_id},#{type},#{state},#{user_id},#{passenger_num},#{datetime}," +
            "#{from_name},#{to_name},#{from_lon},#{from_lat},#{to_lon},#{to_lat},#{driver_id},#{description})")
    void saveOrder(Order order);
}
