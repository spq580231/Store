package cn.tedu.store.controller;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.ResponseResult;
import cn.tedu.store.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/address")
public class AddressController extends BaseController {

    @Autowired
    private IAddressService addressService;

    @RequestMapping(value = "/addnew.do", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult<Void> handleAddnew(Address address, HttpSession session) {
        // 获取uid
        Integer uid = getUidFromSession(session);
        System.out.println(uid);


        // 将uid封装到address
        address.setUid(uid);
        // 调用业务层执行增加
        addressService.addnew(address);
        // 返回
        return new ResponseResult<Void>();
    }

    @RequestMapping("/list.do")
    @ResponseBody
    public ResponseResult<List<Address>> showList(HttpSession session) {
        // 1. 获取数据
        Integer uid = getUidFromSession(session);
        List<Address> list = addressService.getList(uid);
        // 2. 创建返回值
        ResponseResult<List<Address>> rr = new ResponseResult<List<Address>>();
        // 3. 将数据封装到返回值对象
        rr.setData(list);
        // 4. 执行返回
        return rr;
    }

    @RequestMapping("/set_default.do")
    @ResponseBody
    public ResponseResult<Void> setDefault(@RequestParam("id") Integer id, HttpSession session) {
        Integer uid = getUidFromSession(session);
        addressService.setDefaultAddress(id, uid);
        return new ResponseResult<Void>();
    }

    @RequestMapping("/delete.do")
    @ResponseBody
    public ResponseResult<Void> delete(@RequestParam("id") Integer id, HttpSession session) {
        Integer uid = getUidFromSession(session);
        addressService.delete(id, uid);
        return new ResponseResult<Void>();
    }

    @RequestMapping("/update.do")
    @ResponseBody
    public ResponseResult<Void> update(@RequestParam("id") Integer id, HttpSession session) {
        Integer uid = getUidFromSession(session);
        addressService.updateAddressById(id);
        return new ResponseResult<Void>();
    }


    @RequestMapping("/getAddressId.do")
    @ResponseBody
    public ResponseResult<Void> getAddressId(@RequestParam("id") Integer id, HttpSession session) {
        session.setAttribute("AddressId",id);
        System.out.println("AddressId:"+id);
        return new ResponseResult<Void>();
    }

     @RequestMapping("/sendAddressId.do")
    @ResponseBody
    public ResponseResult<Integer> sendAddressId(@RequestParam("AddressId") String AddressId, HttpSession session) {
        Integer id= (Integer) session.getAttribute(AddressId);
        ResponseResult<Integer> rr = new ResponseResult<Integer>();
        rr.setData(id);
         System.out.println("rr:"+rr);
         return rr;
    }

    @RequestMapping("/findAddressById.do")
    @ResponseBody
    public ResponseResult<Address> findAddressById(@RequestParam("id") Integer id, HttpSession session) {
        Integer uid = getUidFromSession(session);
        addressService.findAddressFromId(id);
        Address list = addressService.getAddressById(id);
        System.out.println(list);
        ResponseResult<Address> rr = new ResponseResult<Address>();
        rr.setData(list);
        return rr;
    }

}





