package com.padr.tuxdb.engine.process.pvt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.padr.tuxdb.engine.storage.folder.BtreeFolderStorageElement;
import com.padr.tuxdb.engine.storage.file.BtreeFileStorageElement;
import com.padr.tuxdb.engine.storage.file.ExternalNodeFileStorageElement;
import com.padr.tuxdb.engine.storage.file.FileStorageElement;
import com.padr.tuxdb.engine.storage.file.InternalNodeFileStorageElement;

public class Btree implements Iterable<Map<String, Object>> {

    private static String generateId() {
        String charset = "0123456789abcdef";

        Random random = new Random();

        String id = "";

        for (int i = 0; i < 24; i++) {
            int charIndex = random.nextInt(charset.length());

            id += charset.charAt(charIndex);
        }

        return id;
    }

    private static class ExternalNode {

        private static ExternalNode create(Btree btree, boolean isLeaf) throws IOException {

            String id = generateId();

            ExternalNodeFileStorageElement externalNodeFileStorageElement = new ExternalNodeFileStorageElement(
                    btree.databaseName, btree.collectionName, id);

            externalNodeFileStorageElement.create();

            Map<String, Object> updateData = new HashMap<>();

            updateData.put("isLeaf", isLeaf);

            externalNodeFileStorageElement.update(updateData);

            return new ExternalNode(btree.databaseName, btree.collectionName, id);
        }

        private ExternalNodeFileStorageElement externalNodeFileStorageElement;

        private String databaseName;
        private String collectionName;

        public ExternalNode(String databaseName, String collectionName, String id) throws IOException {
            externalNodeFileStorageElement = new ExternalNodeFileStorageElement(databaseName, collectionName, id);

            this.databaseName = databaseName;
            this.collectionName = collectionName;
        }

        public void delete() throws IOException {
            externalNodeFileStorageElement.delete();
        }

        @SuppressWarnings("unchecked")
        public String getId() throws IOException {
            return (String) ((Map<String, Object>) externalNodeFileStorageElement.read()).get("_id");
        }

        @SuppressWarnings("unchecked")
        public List<InternalNode> getInternalNodes() throws IOException {
            List<InternalNode> internalNodes = new ArrayList<>();

            List<String> internalNodesId = (List<String>) ((Map<String, Object>) externalNodeFileStorageElement.read())
                    .get("internalNodesId");

            for (int i = 0; i < internalNodesId.size(); i++)
                internalNodes.add(i, new InternalNode(databaseName, collectionName, internalNodesId.get(i)));

            return internalNodes;
        }

        public int getInternalNodeSize() throws IOException {
            return getIsLeaf() ? getInternalNodes().size() : getInternalNodes().size() - 1;
        }

        @SuppressWarnings("unchecked")
        public boolean getIsLeaf() throws IOException {
            return (boolean) ((Map<String, Object>) externalNodeFileStorageElement.read()).get("isLeaf");
        }

        public void setInternalNodes(List<InternalNode> internalNodes) throws IOException {
            List<String> internalNodesId = new ArrayList<>();

            for (int i = 0; i < internalNodes.size(); i++)
                internalNodesId.add(i, internalNodes.get(i).getId());

            Map<String, Object> updateData = new HashMap<>();

            updateData.put("internalNodesId", internalNodesId);

            externalNodeFileStorageElement.update(updateData);
        }

        // public void setIsLeaf(boolean isLeaf) throws IOException {
        // if (!isExist())
        // throw new
        // FileNotFoundException(ProcessMessage.BtreeMessage.ErrorMessage.ERROR_1);

        // Map<String, Object> updateData = new HashMap<>();

        // updateData.put("isLeaf", isLeaf);

        // externalNodeStaticStorageElement.update(updateData);
        // }

        public boolean isExist() throws IOException {
            return externalNodeFileStorageElement.isExist();
        }
    }

    private static class InternalNode {

        private static InternalNode create(Btree btree, Map<String, Object> data, String linkId) throws IOException {

            String id = generateId();

            InternalNodeFileStorageElement internalNodeFileStorageElement = new InternalNodeFileStorageElement(
                    btree.databaseName, btree.collectionName, id);

            internalNodeFileStorageElement.create();

            if (data != null) {
                LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>(data);

                data.clear();
                data.put("_id", id);
                data.putAll(linkedHashMap);
            }

            Map<String, Object> updateData = new HashMap<>();

            updateData.put("data", data);
            updateData.put("linkId", linkId);

            internalNodeFileStorageElement.update(updateData);

            return new InternalNode(btree.databaseName, btree.collectionName, id);
        }

        InternalNodeFileStorageElement internalNodeFileStorageElement;

        String databaseName;
        String collectionName;

        public InternalNode(String databaseName, String collectionName, String id) {
            internalNodeFileStorageElement = new InternalNodeFileStorageElement(databaseName, collectionName, id);

            this.databaseName = databaseName;
            this.collectionName = collectionName;
        }

        public void delete() throws IOException {
            internalNodeFileStorageElement.delete();
        }

        @SuppressWarnings("unchecked")
        public String getId() throws IOException {
            return (String) ((Map<String, Object>) internalNodeFileStorageElement.read()).get("_id");
        }

        @SuppressWarnings("unchecked")
        public Map<String, Object> getData() throws IOException {
            Map<String, Object> data = (Map<String, Object>) ((Map<String, Object>) internalNodeFileStorageElement
                    .read()).get("data");

            return data;
        }

        @SuppressWarnings("unchecked")
        public ExternalNode getLink() throws IOException {
            String linkId = (String) ((Map<String, Object>) internalNodeFileStorageElement.read()).get("linkId");

            return new ExternalNode(databaseName, collectionName, linkId);
        }

        public void setId(String id) throws IOException {
            Map<String, Object> updateData = new HashMap<>();

            updateData.put("_id", id);

            internalNodeFileStorageElement.update(updateData);
            internalNodeFileStorageElement.setDomain(String.format("%s.%s", id, FileStorageElement.FILE_EXTENSION));
        }

        public void setData(Map<String, Object> data) throws IOException {
            Map<String, Object> updateData = new HashMap<>();

            updateData.put("data", data);

            internalNodeFileStorageElement.update(updateData);
        }

        public void setLink(String linkId) throws IOException {
            Map<String, Object> updateData = new HashMap<>();

            updateData.put("linkId", linkId);

            internalNodeFileStorageElement.update(updateData);
        }

        public boolean contains(Map<String, Object> query) throws IOException {
            if (query.isEmpty())
                return false;

            Map<String, Object> data = getData();

            for (Map.Entry<String, Object> queryElement : query.entrySet()) {
                if (!data.containsKey(queryElement.getKey())
                        || !data.get(queryElement.getKey()).equals(queryElement.getValue()))
                    return false;
            }

            return true;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            else if (!(obj instanceof InternalNode))
                return false;
            else if (this == obj)
                return true;

            InternalNode internalNode = (InternalNode) obj;

            try {
                return getId().equals(internalNode.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }
    }

    private BtreeFileStorageElement btreeFileStorageElement;

    private String databaseName;
    private String collectionName;

    public static Btree creatBtree(String databaseName, String collectionName) throws IOException {
        new BtreeFolderStorageElement(databaseName, collectionName).create();

        return new Btree(databaseName, collectionName);
    }

    public Btree(String databaseName, String collectionName) throws IOException {
        btreeFileStorageElement = new BtreeFileStorageElement(databaseName, collectionName);

        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    @SuppressWarnings("unchecked")
    private ExternalNode getRoot() throws IOException {
        String rootId = (String) ((Map<String, Object>) btreeFileStorageElement.read()).get("rootId");

        return new ExternalNode(databaseName, collectionName, rootId);
    }

    @SuppressWarnings("unchecked")
    private int getMinL() throws IOException {
        return ((Double) ((Map<String, Object>) btreeFileStorageElement.read()).get("minL")).intValue();
    }

    @SuppressWarnings("unchecked")
    private int getMaxL() throws IOException {
        return ((Double) ((Map<String, Object>) btreeFileStorageElement.read()).get("maxL")).intValue();
    }

    private void setRoot(String rootId) throws IOException {
        Map<String, Object> updateData = new HashMap<>();

        updateData.put("rootId", rootId);

        btreeFileStorageElement.update(updateData);
    }

    // B-Tree auixiliary functions

    private void partition(ExternalNode root, int index) throws Throwable {
        List<InternalNode> rootInternalNodes = root.getInternalNodes();

        int minL = getMinL();
        int maxL = getMaxL();

        ExternalNode partitioningExternalNode = rootInternalNodes.get(index).getLink();

        List<InternalNode> partitioningExternalNodeInternalNodes = partitioningExternalNode.getInternalNodes();
        boolean partitioningExternalNodeIsLeaf = partitioningExternalNode.getIsLeaf();

        // Create left external node and right external node

        ExternalNode left = ExternalNode.create(this, partitioningExternalNodeIsLeaf);
        ExternalNode right = ExternalNode.create(this, partitioningExternalNodeIsLeaf);

        List<InternalNode> leftInternalNodes = left.getInternalNodes();
        List<InternalNode> rightInternalNodes = right.getInternalNodes();

        // Fill the internal nodes of left and right

        for (int i = 0; i < minL - 1; i++) {
            leftInternalNodes.add(i, partitioningExternalNodeInternalNodes.get(i));
            rightInternalNodes.add(i, partitioningExternalNodeInternalNodes.get(minL + i));
        }

        left.setInternalNodes(leftInternalNodes);
        right.setInternalNodes(rightInternalNodes);

        // Median internal node

        InternalNode medianInternalNode = partitioningExternalNodeInternalNodes.get(minL - 1);

        // Set the link of the left and right external node's last internal node

        if (!partitioningExternalNodeIsLeaf) {
            leftInternalNodes.add(minL - 1, InternalNode.create(this, null, medianInternalNode.getLink().getId()));
            rightInternalNodes.add(minL - 1, InternalNode.create(this, null,
                    partitioningExternalNodeInternalNodes.get(maxL - 1).getLink().getId()));
        }

        // Set the median to the root

        rootInternalNodes.add(index, medianInternalNode);

        root.setInternalNodes(rootInternalNodes);

        rootInternalNodes.get(index).setLink(left.getId());
        rootInternalNodes.get(index + 1).setLink(right.getId());

        // Delete the partitioning external node

        partitioningExternalNode.delete();
    }

    private void combine(ExternalNode root, int index) throws Throwable {
        List<InternalNode> rootInternalNodes = root.getInternalNodes();

        ExternalNode left = rootInternalNodes.get(index).getLink();
        ExternalNode right = rootInternalNodes.get(index + 1).getLink();
        InternalNode median = rootInternalNodes.get(index);

        List<InternalNode> leftInternalNodes = left.getInternalNodes();
        List<InternalNode> rightInternalNodes = right.getInternalNodes();

        int leftInternalNodeSize = left.getInternalNodeSize();

        ExternalNode newExternalNode = ExternalNode.create(this, left.getIsLeaf());
        List<InternalNode> internalNodesOfNewExternalNode = newExternalNode.getInternalNodes();

        for (InternalNode internalNode : leftInternalNodes)
            internalNodesOfNewExternalNode.add(internalNode);

        if (!left.getIsLeaf()) {
            median.setLink(leftInternalNodes.get(leftInternalNodeSize).getLink().getId());

            internalNodesOfNewExternalNode.set(leftInternalNodeSize, median);

            leftInternalNodes.get(leftInternalNodeSize).delete();
        } else
            internalNodesOfNewExternalNode.add(median);

        for (InternalNode internalNode : rightInternalNodes)
            internalNodesOfNewExternalNode.add(internalNode);

        rootInternalNodes.remove(index);
        rootInternalNodes.get(index).setLink(newExternalNode.getId());

        left.delete();
        right.delete();

        root.setInternalNodes(rootInternalNodes);
        newExternalNode.setInternalNodes(internalNodesOfNewExternalNode);
    }

    private void edit(ExternalNode root, int index) throws Throwable {
        int minL = getMinL();

        List<InternalNode> rootInternalNodes = root.getInternalNodes();

        if (rootInternalNodes.get(index).getLink().getInternalNodeSize() >= minL - 1)
            return;
        else if (index > 0 && rootInternalNodes.get(index - 1).getLink().getInternalNodeSize() > minL - 1) {
            ExternalNode left = rootInternalNodes.get(index - 1).getLink();
            ExternalNode right = rootInternalNodes.get(index).getLink();

            List<InternalNode> leftInternalNodes = left.getInternalNodes();
            List<InternalNode> rightInternalNodes = right.getInternalNodes();

            int leftInternalNodeSize = left.getInternalNodeSize();

            boolean rightIsLeaf = right.getIsLeaf();

            // Edit the right external node

            rightInternalNodes.add(0, rootInternalNodes.get(index - 1));

            if (rightIsLeaf)
                rightInternalNodes.get(0).setLink("0");
            else
                rightInternalNodes.get(0).setLink(leftInternalNodes.get(leftInternalNodeSize).getLink().getId());

            right.setInternalNodes(rightInternalNodes);

            // Edit root and left external node

            if (!rightIsLeaf)
                leftInternalNodes.get(leftInternalNodeSize)
                        .setLink(leftInternalNodes.get(leftInternalNodeSize - 1).getLink().getId());

            rootInternalNodes.set(index - 1, leftInternalNodes.get(leftInternalNodeSize - 1));
            rootInternalNodes.get(index - 1).setLink(left.getId());

            leftInternalNodes.remove(leftInternalNodeSize - 1);

            root.setInternalNodes(rootInternalNodes);
            left.setInternalNodes(leftInternalNodes);
        } else if (index < root.getInternalNodeSize()
                && rootInternalNodes.get(index + 1).getLink().getInternalNodeSize() > minL - 1) {

            ExternalNode left = rootInternalNodes.get(index).getLink();
            ExternalNode right = rootInternalNodes.get(index + 1).getLink();

            List<InternalNode> leftInternalNodes = left.getInternalNodes();
            List<InternalNode> rightInternalNodes = right.getInternalNodes();

            int leftInternalNodeSize = left.getInternalNodeSize();
            boolean leftIsLeaf = left.getIsLeaf();

            // Edit the left external node

            if (leftIsLeaf) {
                leftInternalNodes.add(leftInternalNodeSize, rootInternalNodes.get(index));
                leftInternalNodes.get(leftInternalNodeSize).setLink("0");
            } else {
                leftInternalNodes.add(leftInternalNodeSize + 1, rootInternalNodes.get(index));
                leftInternalNodes.get(leftInternalNodeSize + 1)
                        .setLink(leftInternalNodes.get(leftInternalNodeSize).getLink().getId());

                leftInternalNodes.get(leftInternalNodeSize).delete();
                leftInternalNodes.remove(leftInternalNodeSize);

                leftInternalNodes.add(leftInternalNodeSize + 1,
                        InternalNode.create(this, null, rightInternalNodes.get(0).getLink().getId()));
            }

            left.setInternalNodes(leftInternalNodes);

            // Edit root

            rootInternalNodes.set(index, rightInternalNodes.get(0));
            rootInternalNodes.get(index).setLink(left.getId());

            root.setInternalNodes(rootInternalNodes);

            // Edit right external node

            rightInternalNodes.remove(0);

            right.setInternalNodes(rightInternalNodes);
        } else {
            if (index > 0 && rootInternalNodes.get(index - 1).getLink().getInternalNodeSize() <= minL - 1)
                combine(root, index - 1);
            else
                combine(root, index);
        }
    }

    private void increaseHeight() throws Throwable {
        ExternalNode root = getRoot();

        if (root.getInternalNodeSize() == getMaxL() - 1) {
            ExternalNode newRoot = ExternalNode.create(this, false);

            List<InternalNode> newRootInternalNodes = newRoot.getInternalNodes();

            newRootInternalNodes.add(0, InternalNode.create(this, null, root.getId()));

            newRoot.setInternalNodes(newRootInternalNodes);

            setRoot(newRoot.getId());

            partition(newRoot, 0);
        }
    }

    private void decreaseHeight() throws Throwable {
        ExternalNode root = getRoot();

        if (!root.getIsLeaf()) {
            if (root.getInternalNodeSize() == 1) {
                int minL = getMinL();
                List<InternalNode> rootInternalNodes = root.getInternalNodes();

                if (rootInternalNodes.get(0).getLink().getInternalNodeSize() <= minL - 1
                        && rootInternalNodes.get(1).getLink().getInternalNodeSize() <= minL - 1) {
                    combine(root, 0);

                    rootInternalNodes = root.getInternalNodes();

                    setRoot(rootInternalNodes.get(0).getLink().getId());

                    rootInternalNodes.get(0).delete();
                    root.delete();
                }
            }
        }
    }

    private InternalNode max(ExternalNode root) throws IOException {
        if (!root.isExist())
            return null;

        if (root.getIsLeaf())
            return root.getInternalNodes().get(root.getInternalNodeSize() - 1);
        else
            return max(root.getInternalNodes().get(root.getInternalNodeSize()).getLink());
    }

    private void deleteMax(ExternalNode root) throws Throwable {
        if (!root.isExist())
            return;

        List<InternalNode> rootInternalNodes = root.getInternalNodes();
        int rootInternalNodeSize = root.getInternalNodeSize();

        if (root.getIsLeaf()) {
            InternalNode max = rootInternalNodes.get(rootInternalNodeSize - 1);

            max.delete();

            rootInternalNodes.remove(rootInternalNodeSize - 1);

            root.setInternalNodes(rootInternalNodes);

            return;
        } else
            deleteMax(root.getInternalNodes().get(root.getInternalNodeSize()).getLink());

        edit(root, rootInternalNodeSize);
    }

    private void insert(ExternalNode root, InternalNode newInternalNode) throws Throwable {
        int placeIndex = 0;

        List<InternalNode> rootInternalNodes = root.getInternalNodes();

        for (InternalNode internalNode : rootInternalNodes) {
            if (internalNode.getData().isEmpty() || newInternalNode.getId().compareTo(internalNode.getId()) < 0)
                break;
            else
                placeIndex++;
        }

        if (root.getIsLeaf()) {
            rootInternalNodes.add(placeIndex, newInternalNode);

            root.setInternalNodes(rootInternalNodes);
        } else {
            if (rootInternalNodes.get(placeIndex).getLink().getInternalNodeSize() == getMaxL() - 1) {
                partition(root, placeIndex);

                rootInternalNodes = root.getInternalNodes();

                if (newInternalNode.getId().compareTo(rootInternalNodes.get(placeIndex).getId()) > 0)
                    placeIndex++;
            }

            insert(rootInternalNodes.get(placeIndex).getLink(), newInternalNode);
        }
    }

    private Map<String, Object> get(ExternalNode root, String id) throws IOException {
        if (!root.isExist())
            return new HashMap<>();

        for (InternalNode internalNode : root.getInternalNodes()) {
            if (internalNode.getData().isEmpty() || internalNode.getId().compareTo(id) > 0)
                return get(internalNode.getLink(), id);
            else if (id.compareTo(internalNode.getId()) == 0)
                return internalNode.getData();
        }

        return new HashMap<>();
    }

    private List<Map<String, Object>> get(ExternalNode root, Map<String, Object> query) throws IOException {
        if (!root.isExist())
            return new ArrayList<>();

        List<Map<String, Object>> result = new ArrayList<>();

        for (InternalNode internalNode : root.getInternalNodes()) {
            if (internalNode.contains(query))
                result.add(internalNode.getData());

            try {
                result.addAll(get(internalNode.getLink(), query));
            } catch (NullPointerException e) {
                continue;
            }

        }

        return result;
    }

    private int size(ExternalNode root) throws IOException {
        if (!root.isExist())
            return 0;

        int size = root.getInternalNodeSize();

        for (InternalNode internalNode : root.getInternalNodes())
            size += size(internalNode.getLink());

        return size;
    }

    private void delete(ExternalNode root, String id) throws Throwable {
        if (!root.isExist())
            return;

        List<InternalNode> rootInternalNodes = root.getInternalNodes();

        int index = 0;

        for (InternalNode internalNode : rootInternalNodes) {
            if (internalNode.getData().isEmpty() || id.compareTo(internalNode.getId()) < 0)
                delete(internalNode.getLink(), id);
            else if (id.compareTo(internalNode.getId()) == 0) {
                if (root.getIsLeaf()) {
                    rootInternalNodes.remove(internalNode);

                    internalNode.delete();

                    root.setInternalNodes(rootInternalNodes);

                    return;
                } else {
                    InternalNode predecessorInternalNode = max(internalNode.getLink());

                    String idOfPredecessorInternalNode = predecessorInternalNode.getId();
                    Map<String, Object> dataOfPredecessorInternalNode = predecessorInternalNode.getData();

                    deleteMax(internalNode.getLink());

                    internalNode.setId(idOfPredecessorInternalNode);
                    internalNode.setData(dataOfPredecessorInternalNode);

                    root.setInternalNodes(rootInternalNodes);
                }
            } else {
                index++;

                continue;
            }

            break;
        }

        edit(root, index);
    }

    private List<Map<String, Object>> iteratorDataInit(ExternalNode root) throws IOException {
        List<Map<String, Object>> iteratorData = new ArrayList<>();

        if (!root.isExist())
            return iteratorData;

        for (InternalNode internalNode : root.getInternalNodes()) {
            Map<String, Object> data = internalNode.getData();

            if (!data.isEmpty())
                iteratorData.add(data);

            try {
                iteratorData.addAll(iteratorDataInit(internalNode.getLink()));
            } catch (NullPointerException e) {
                continue;
            }
        }

        return iteratorData;
    }

    // B-Tree main functions

    public void insert(Map<String, Object> data) throws Throwable {
        if (!(getRoot().isExist())) {
            ExternalNode root = ExternalNode.create(this, true);

            setRoot(root.getId());
        }

        increaseHeight();
        insert(getRoot(), InternalNode.create(this, data, null));
    }

    // This function has O(logN) runtime
    public Map<String, Object> get(String id) throws IOException {
        return get(getRoot(), id);
    }

    // This function has O(N) runtime
    public List<Map<String, Object>> get(Map<String, Object> query) throws IOException {
        return get(getRoot(), query);
    }

    public int size() throws IOException {
        return size(getRoot());
    }

    // This function has O(logN) runtime
    public boolean delete(String id) throws Throwable {
        if (get(id).isEmpty())
            return false;

        delete(getRoot(), id);
        decreaseHeight();

        return true;
    }

    // This function has O(N) runtime
    public boolean delete(Map<String, Object> query) throws Throwable {
        List<Map<String, Object>> queryResult = get(query);

        if (queryResult.isEmpty())
            return false;

        for (Map<String, Object> data : queryResult)
            delete(String.valueOf(data.get("_id")));

        return true;
    }

    // This function has O(logN) runtime
    public boolean update(String id, Map<String, Object> updateData) throws IOException {
        Map<String, Object> data = get(id);

        if (data.isEmpty())
            return false;

        for (Map.Entry<String, Object> updateElement : updateData.entrySet())
            data.put(updateElement.getKey(), updateElement.getValue());

        new InternalNode(databaseName, collectionName, id).setData(data);

        return true;
    }

    // This function has O(N) runtime
    public boolean update(Map<String, Object> query, Map<String, Object> updateData) throws IOException {
        List<Map<String, Object>> queryResult = get(query);

        if (queryResult.isEmpty())
            return false;

        Map<String, Object> data = queryResult.get(0);

        for (Map.Entry<String, Object> updateElement : updateData.entrySet())
            data.put(updateElement.getKey(), updateElement.getValue());

        new InternalNode(databaseName, collectionName, String.valueOf(data.get("_id"))).setData(data);

        return true;
    }

    public Iterator<Map<String, Object>> iterator() {
        List<Map<String, Object>> iteratorData = new ArrayList<>();

        try {
            iteratorData = iteratorDataInit(getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return iteratorData.iterator();
    }
}
