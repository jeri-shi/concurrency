package concurrency;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TestWithMockito {
  
  @Mock
  private List<String> listMock;
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
  }
  
  @Test
  public void testBehavior() {
    List<String> mList = listMock;
    
    mList.add("one");
    mList.clear();
    
    verify(mList).add("one");
    verify(mList).clear();
    
  }
  
  @Test
  public void testLinkedList() {
    @SuppressWarnings("unchecked")
    LinkedList<String> list = mock(LinkedList.class); 
    
    when(list.get(0)).thenReturn("Frist");
    when(list.get(1)).thenThrow(new IllegalArgumentException(""));
    
    assertThat(list.get(0), equalTo("Frist"));
    thrown.expect(IllegalArgumentException.class);
    list.get(1);
    
  }
  
  @Test
  public void testArgsMatches() {
    @SuppressWarnings("unchecked")
    List<String> list = mock(List.class);
    
    when(list.get(anyInt())).thenReturn("element");
    when(list.contains(anyString())).thenReturn(true);
    
    assertThat(list.get(3), equalTo("element"));
    verify(list).get(anyInt());
  }
  
  @Test
  public void testMulti() {
    @SuppressWarnings("unchecked")
    List<String> list = mock(List.class);
    
    list.add("1");
    list.add("2");
    list.add("2");
    list.add("3");
    list.add("3");
    list.add("3");
    
    verify(list).add("1");
    verify(list, times(2)).add("2");
    verify(list, times(3)).add("3");
    
    verify(list, never()).add("never");
    
    verify(list, atLeast(2)).add("3");
    verify(list, atMost(2)).add("1");
    verify(list, atLeastOnce()).add("1");
    
  }
  
  @Test
  public void testVoidException() {
    @SuppressWarnings("unchecked")
    List<String> list = mock(List.class);
    
    doThrow(new IllegalArgumentException()).when(list).clear();
    
    thrown.expect(IllegalArgumentException.class);
    list.clear();
    
  }
  
  @Test
  public void testVerifyInOrder() {
    List<String> list = listMock;
    
    list.add("First");
    list.add("Second");
    
    InOrder inOrder = Mockito.inOrder(list);
    
    inOrder.verify(list).add("First");
    inOrder.verify(list).add("Second");
    
    
    List<String> list1 = listMock;
    List<String> list2 = mock(List.class);
    
    list1.add("3rd");
    list2.add("4th");
    
    InOrder inorder = Mockito.inOrder(list1, list2);
    inorder.verify(list1).add("3rd");
    inorder.verify(list2).add("4th");
    
    
  }
}
