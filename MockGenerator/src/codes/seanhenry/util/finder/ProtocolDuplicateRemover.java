package codes.seanhenry.util.finder;

import com.jetbrains.swift.psi.SwiftFunctionDeclaration;
import com.jetbrains.swift.psi.SwiftInitializerDeclaration;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;
import com.jetbrains.swift.psi.SwiftVariableDeclaration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ProtocolDuplicateRemover implements TypeItemFinder {

  private final TypeItemFinder protocolItemFinder;
  private final TypeItemFinder classItemFinder;

  public ProtocolDuplicateRemover(TypeItemFinder protocolItemFinder, TypeItemFinder classItemFinder) {
    this.protocolItemFinder = protocolItemFinder;
    this.classItemFinder = classItemFinder;
  }

  @Override
  public List<SwiftVariableDeclaration> getProperties() {
    return removeDuplicateProtocolItems(protocolItemFinder.getProperties(), classItemFinder.getProperties(), (o1, o2) -> Objects.equals(o1.getVariables().get(0).getName(), o2.getVariables().get(0).getName()) ? 0 : 1);
  }

  @Override
  public List<SwiftFunctionDeclaration> getMethods() {
    return removeDuplicateProtocolItems(protocolItemFinder.getMethods(), classItemFinder.getMethods(), (o1, o2) -> Objects.equals(o1.getSignature(), o2.getSignature()) ? 0 : 1);
  }

  @Override
  public List<SwiftTypeDeclaration> getTypes() {
    return protocolItemFinder.getTypes();
  }

  @Override
  public List<SwiftInitializerDeclaration> getInitialisers() {
    return protocolItemFinder.getInitialisers();
  }

  private <T> List<T> removeDuplicateProtocolItems(List<T> protocolItems, List<T> classItems, Comparator<T> comparator) {
    List<T> unique = new ArrayList<>(protocolItems);
    for (T protocolItem : protocolItems) {
      for (T classItem : classItems) {
        if (comparator.compare(protocolItem, classItem) == 0) {
          unique.remove(protocolItem);
          break;
        }
      }
    }
    return unique;
  }
}
