package com.thirdeye.ignite
import org.apache.ignite._
import org.apache.ignite.configuration._
import org.apache.ignite.spark._
import org.apache.spark._

object IgniteDemo {
  
  def main(args: Array[String]) = {
  val ignite = Ignition.start("examples/config/example-ignite.xml") 
  val sc = new SparkContext("local[2]","itest")
    val igniteContext = new IgniteContext[Integer, Integer](sc, 
    () => new IgniteConfiguration())
    var cache = igniteContext.fromCache("partitioned")
   cache.savePairs(sc.parallelize(1 to 10000, 10).map(i => (i, i)))
   var result = cache.filter(_._2 >= 5000).collect()
   println("partitioned: " + result.mkString(","))
    cache = igniteContext.fromCache("partitioned")
    //result = cache.sql("select _val from Integer " +
    // " where val > ? and val < ?", 10, 100)
   //println(result.mkString(","))
  }
}

