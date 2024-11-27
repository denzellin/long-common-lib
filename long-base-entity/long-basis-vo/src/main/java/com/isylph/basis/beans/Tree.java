package com.isylph.basis.beans;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Tree extends BaseVO {

    public static final Long ROOT_FID = 0L;

    private Long id;

    private Long fid;

    /**
     * 内部编码, 顶级节点的Code为.000.id.
     * 其他节点的code为上级code+id
     */
    private String code;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 子树
     */
    private List<Tree> children;

    static String[] getCodeArray(String code){
        if (code == null) {
            return new String[0];
        }


        return code.split("\\.");
    }

    /**
     * 获取树形记录的Code
     * 顶级记录的code为".000.id"
     * 后续等级的记录的code为父节点的code + 本节点ID + "."
     * @param
     * @param
     * @return
     */
    public static String getCode(String parentCode, Long id){
        if (id == null){
            return null;
        }

        if (parentCode == null || parentCode.length() == 0){
            return ".000." + id + ".";
        }
        return parentCode + id + ".";
    }

    public static Long getFatherIdFromCode(String code) {

        String[] codeArr = getCodeArray(code);

        if (null== codeArr || codeArr.length < 3) {
            return null;
        } else {
            Long uid = Long.parseLong(codeArr[codeArr.length - 2]);
            return uid;
        }
    }

    public static List<Long> getIdsFromCode(String code) {

        String[] codeArr = getCodeArray(code);

        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < codeArr.length; i++) {
            if (codeArr[i] == null || codeArr[i].equals("")) {
                continue;
            }

            Long uid = Long.parseLong(codeArr[i]);
            ids.add(uid);
        }

        return ids;
    }

    public static int getLevel(String code) {
        if(code == null || code.length() == 0){
            return 0;
        }

        int index;
        int count = 0;
        while ((index = code.indexOf(".")) != -1 ) {
            code = code.substring(index + 1);
            count++;
        }
        if (count <=3 ){
            return 0;
        }
        return count - 3;
    }



    private static <T extends Tree> List<T> getChildren(List<T> source, Long fid){
        Iterator<T> it = source.iterator();
        List<T> children = new ArrayList<>();

        while (it.hasNext()) {
            T item = it.next();
            if (fid.equals(item.getFid())){
                children.add(item);
                it.remove();
            }
        }
        if (children.size() == 0){
            return null;
        }
        return children;
    }

    /**
     *
     * @param source: 待加入children的清单
     * @param list: 上级节点清单
     * @param <T>
     * @return
     */
    private static <T extends Tree> List<T> appendChild(List<T> source, List<T> list){

        if (null == source || source.size() == 0) {
            return list;
        }

        T node = list.get(list.size() - 1);
        List<T> children = getChildren(source, node.getId());

        if ( children == null || children.size() == 0){
            return list;
        }

        for(int i = 0; i < children.size(); i ++){
            list.add(children.get(i));
            list = appendChild(source, list);
        }

        return list;
    }

    public static <T extends Tree> List<T> sortHierarchy(List<T> vos) {

        /*如果节点数为0或1，则不需要组装*/
        if (vos == null || vos.size() < 2) {
            return vos;
        }

        for (T vo: vos){
            int level = getLevel(vo.getCode());
            vo.setLevel(level);
        }

        List<T> retList = new ArrayList<>();

        List<T> rootLevelList = new ArrayList<>();
        Iterator<T> it = vos.iterator();
        while (it.hasNext()) {
            T item = it.next();
            if (Objects.equals(item.getFid(), ROOT_FID)){
                rootLevelList.add(item);
                it.remove();
            }
        }

        for ( int i=0; i < rootLevelList.size(); i++){
            retList.add(rootLevelList.get(i));
            retList = appendChild(vos, retList);
        }


        return retList;
    }


    public static <T extends Tree> List<T> assembleTree (List<T> vos) {

        /*如果节点数为0或1，则不需要组装*/
        if ( vos == null || vos.size() == 0) {
            return vos;
        }

        List<T> retList = new ArrayList<>();
        Map<Long, T> map = new HashMap<>();
        long rootFid = Optional.ofNullable(vos.get(0).getFid()).orElse(0L);
        for (T item : vos) {
            int level = getLevel(item.getCode());
            item.setLevel(level);
            map.put(item.getId(), item);
            if (item.getFid() == null || item.getFid() == rootFid ) {
                retList.add(item);
                continue;
            }

            T fNode = map.get(item.getFid());
            if (null != fNode) {

                List<Tree> subs = fNode.getChildren();
                if (null == subs) {
                    subs = new ArrayList<>();
                    fNode.setChildren(subs);
                }

                subs.add(item);
            } else {
                log.error("Failed to get father node: {}", item);
            }
        }

        return retList;
    }

}
