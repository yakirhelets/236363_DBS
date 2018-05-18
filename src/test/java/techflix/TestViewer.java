//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package techflix;

import org.junit.Assert;
import org.junit.Test;
import techflix.business.ReturnValue;
import techflix.business.Viewer;

public class TestViewer extends AbstractTest {
    public TestViewer() {
    }

    @Test
    public void testCreateUser() {
        Viewer badViewer = Viewer.badViewer();
        ReturnValue actual = Solution.createViewer(badViewer);
        Assert.assertEquals(ReturnValue.BAD_PARAMS, actual);
        badViewer.setId(1);
        actual = Solution.createViewer(badViewer);
        Assert.assertEquals(ReturnValue.BAD_PARAMS, actual);
        badViewer.setId(-1);
        badViewer.setName("viewer1");
        actual = Solution.createViewer(badViewer);
        Assert.assertEquals(ReturnValue.BAD_PARAMS, actual);
        Viewer viewer1 = TestUtils.generateViewer(Integer.valueOf(1));
        actual = Solution.createViewer(viewer1);
        Assert.assertEquals(ReturnValue.OK, actual);
        actual = Solution.createViewer(viewer1);
        Assert.assertEquals(ReturnValue.ALREADY_EXISTS, actual);
    }

    @Test
    public void testGetUser() {
        Viewer actualViewer = Solution.getViewer(Integer.valueOf(1));
        Assert.assertEquals(Viewer.badViewer(), actualViewer);
        Viewer viewer1 = TestUtils.generateViewer(Integer.valueOf(1));
        Solution.createViewer(viewer1);
        actualViewer = Solution.getViewer(Integer.valueOf(1));
        Assert.assertEquals(viewer1, actualViewer);
        actualViewer = Solution.getViewer(Integer.valueOf(-1));
        Assert.assertEquals(Viewer.badViewer(), actualViewer);
    }

    @Test
    public void testDeleteUser() {
        Viewer viewer1 = TestUtils.generateViewer(Integer.valueOf(1));
        Viewer viewer2 = TestUtils.generateViewer(Integer.valueOf(2));
        ReturnValue actual = Solution.deleteViewer(viewer1);
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Solution.createViewer(viewer1);
        Solution.createViewer(viewer2);
        actual = Solution.deleteViewer(viewer1);
        Assert.assertEquals(ReturnValue.OK, actual);
        Viewer returnedViewer = Solution.getViewer(Integer.valueOf(2));
        Assert.assertEquals(viewer2, returnedViewer);
        actual = Solution.deleteViewer(Viewer.badViewer());
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
    }

    @Test
    public void testUpdateUser() {
        Viewer badViewer = Viewer.badViewer();
        ReturnValue actual = Solution.updateViewer(badViewer);
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        badViewer.setName("bad");
        actual = Solution.updateViewer(badViewer);
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        badViewer.setId(1);
        badViewer.setName((String)null);
        actual = Solution.updateViewer(badViewer);
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Viewer viewer1 = TestUtils.generateViewer(Integer.valueOf(1));
        actual = Solution.updateViewer(viewer1);
        Assert.assertEquals(ReturnValue.NOT_EXISTS, actual);
        Solution.createViewer(viewer1);
        actual = Solution.updateViewer(viewer1);
        Assert.assertEquals(ReturnValue.OK, actual);
        viewer1.setName((String)null);
        actual = Solution.updateViewer(viewer1);
        Assert.assertEquals(ReturnValue.BAD_PARAMS, actual);
        viewer1.setName("viewer2");
        actual = Solution.updateViewer(viewer1);
        Assert.assertEquals(ReturnValue.OK, actual);
        actual = Solution.updateViewer(viewer1);
        Assert.assertEquals(ReturnValue.OK, actual);
        Assert.assertEquals(viewer1, Solution.getViewer(Integer.valueOf(1)));
    }
}
